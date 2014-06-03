#import "Vigenere.h"
#import "Caesar.h"

@implementation Vigenere


- (void)encrypt:(NSString *)key {
    
    _openText = [_openText lowercaseString];
    
    NSMutableString *result = [NSMutableString string];
    char letter, encryptedLetter, shift;
    
    for (int i = 0, keyIndex = 0; i < [_openText length]; i++) {
        shift = [key characterAtIndex:(keyIndex % [key length])] - 'a';
        
        letter = [_openText characterAtIndex:i];
        
        if (letter >= 'a' && letter <= 'z') {
            encryptedLetter = letter + shift;
            
            if (encryptedLetter > 'z' || encryptedLetter < 'a')
                encryptedLetter -= 26;
            
            [result appendFormat:@"%c", encryptedLetter];
            keyIndex++;
        }
        else if (letter == ' ' || (letter >= '0' && letter <= '9')) {
            [result appendFormat:@"%c", letter];
        }
    }
    _codedText = [[NSString stringWithString: result] uppercaseString];
}

- (void)decrypt:(NSString *)key {
    
    _codedText = [_codedText uppercaseString];
    
    NSMutableString *result = [NSMutableString string];
    char letter, decryptedLetter, shift;
    
    for (int i = 0, keyIndex = 0; i < [_codedText length]; i++) {
        shift = [key characterAtIndex:(keyIndex % [key length])] - 'a';
        
        letter = [_codedText characterAtIndex:i];
        
        if (letter >= 'A' && letter <= 'Z') {
            decryptedLetter = letter - shift;
            
            if (decryptedLetter < 'A' || decryptedLetter > 'Z')
                decryptedLetter += 26;
            
            [result appendFormat:@"%c", decryptedLetter];
            keyIndex++;
        }
        else if (letter == ' ' || (letter >= '0' && letter <= '9')) {
            [result appendFormat:@"%c", letter];
        }
    }
    _openText = [[NSString stringWithString: result] lowercaseString];
}


- (void)convertCodedText {
    _codedText = [_codedText uppercaseString];
    NSMutableString *result = [NSMutableString string];
    
    for (int i = 0; i < [_codedText length]; i++) {
        char letter = [_codedText characterAtIndex:i];
        
        if (letter >= 'A' && letter <= 'Z')
            [result appendFormat:@"%c", letter];
    }
    _codedText = [NSString stringWithString: result];
}


// key length
- (int *)findDistanceDivisors {
    
    [self convertCodedText];
    static int result[19];

    NSString *actualString;
    NSString *actualSequence;
    
    for (int i = 0; i < [_codedText length] - 8; i++) {
        actualString = [_codedText substringFromIndex:i];
        
        for (int j = 7; j >= 4; j--) {
            actualSequence = [actualString substringToIndex:j];
            NSString * restOfText = [actualString substringFromIndex:j];
            
            NSRange range = [restOfText rangeOfString:actualSequence];
            
            if (range.location != NSNotFound) {
                int distance = (int)range.location + j;
                
                for (int k = 2; k <= 20; k++)
                    if (distance % k == 0)
                        result[k - 2]++;
                
                i = i + j;
                break;
            }
        }
    }
    return result;
}

- (int)guessKeyLength {
    
    int result = 1;
    int *divisors = [self findDistanceDivisors];
    int bestMatch = 0;
    
    for (int i = 18; i >= 0; i--) {
        
        if (bestMatch < divisors[i]) {
            
            bestMatch = divisors[i];
            result = i + 2;
        }
    }
    return result;
}


// frequency analysis
- (NSArray *)codedTextSplitted {
    
    NSMutableArray *result = [[NSMutableArray alloc] init];
    int keyLength = [self guessKeyLength];
    
    for (int i = 0; i < keyLength; i++) {
        NSMutableString *splittedText = [[NSMutableString alloc] init];
        
        for (int j = i; j < [_codedText length]; j += keyLength)
            [splittedText appendFormat:@"%c", [_codedText characterAtIndex:j]];
        
        [result addObject:splittedText];
    }
    return result;
}

- (int *)letterFrequency:(char)key from:(NSString *)part {
    
    NSString *decryptedPart = [Caesar decrypt:part with:key];
    static int result[26];
    
    for (char i = 0; i < 26; i++)
        result[i] = 0;
    
    for (int i = 0; i < [decryptedPart length]; i++) {
        char letter = [decryptedPart characterAtIndex:i];
        
        if (letter >= 'a' && letter <= 'z')
            result[letter - 'a']++;
    }
    return result;
}

- (char)guessKeyFor:(NSString *)string {
    
    char bestTip = 'a';
    float actualDifference;
    float smallestDifference = FLT_MAX;
    float expectedFrequency[26] = {8.167, 1.492, 2.782, 4.253, 13.000, 2.228, 2.015, 6.094, 6.966, 0.153, 0.772, 4.025, 2.406, 6.749, 7.507, 1.929, 0.095, 5.987, 6.327, 9.056, 2.758, 0.978, 2.360, 0.150, 1.974, 0.074};
    int * actualFrequency;
    
    for (char i = 0; i < 26; i++) {
        actualDifference = 0.0;
        actualFrequency = [self letterFrequency:'a' + i from:string];
        
        for (char j = 0; j < 26; j++) {
            float letterPercent = ((float)actualFrequency[j] / [string length]) * 100;
            actualDifference += abs(letterPercent - expectedFrequency[j]);
        }
        
        if (actualDifference < smallestDifference) {
            smallestDifference = actualDifference;
            bestTip = 'a' + i;
        }
    }
    return bestTip;
}

- (NSString *)guessKeyLetterFrequency {
    
    NSArray *parts = [self codedTextSplitted];
    NSMutableString *result = [[NSMutableString alloc] init];
    
    for (NSString *part in parts) {
        char actualKey = [self guessKeyFor:part];
        
        [result appendFormat:@"%c", actualKey];
    }
    return result;
}

- (NSString *)letterFrequencyBreak {
    [self decrypt:[self guessKeyLetterFrequency]];
    
    return _openText;
}


//
+ (NSString *)encrypt:(NSString *)string with:(NSString *)key {
    Vigenere *v = [[Vigenere alloc] init];
    [v setOpenText:string];
    [v encrypt:key];
    
    return [v codedText];
}

+ (NSString *)decrypt:(NSString *)string with:(NSString *)key {
    Vigenere *v = [[Vigenere alloc] init];
    [v setCodedText:string];
    [v decrypt:key];
    
    return [v openText];
}

+ (NSString *)breakTheCode:(NSString *)string {
    Vigenere *v = [[Vigenere alloc] init];
    [v setCodedText:string];
    [v convertCodedText];

    NSString *key = [v guessKeyLetterFrequency];
    
    return [Vigenere decrypt:string with:key];
}


@end
