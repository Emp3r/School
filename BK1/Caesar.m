#import "Caesar.h"

@implementation Caesar

// encryption & decryption
- (void)encrypt:(char)key {
    _openText = [_openText lowercaseString];
    
    NSMutableString *result = [NSMutableString string];
    char letter, encryptedLetter;
    char shift = key - 'a';
    
    for (int i = 0; i < [_openText length]; i++) {
        letter = [_openText characterAtIndex:i];
        
        if (letter >= 'a' && letter <= 'z') {
            encryptedLetter = letter + shift;
            
            if (encryptedLetter > 'z' || encryptedLetter < 'a')
                encryptedLetter -= 26;
            
            [result appendFormat:@"%c", encryptedLetter];
        }
        else if (letter == ' ' || (letter >= '0' && letter <= '9')) {
            [result appendFormat:@"%c", letter];
        }
    }
    _codedText = [[NSString stringWithString: result] uppercaseString];
}

- (void)decrypt:(char)key {
    _codedText = [_codedText uppercaseString];
    
    NSMutableString *result = [NSMutableString string];
    char letter, decryptedLetter;
    char shift = key - 'a';
    
    for (int i = 0; i < [_codedText length]; i++) {
        letter = [_codedText characterAtIndex:i];
        
        if (letter >= 'A' && letter <= 'Z') {
            decryptedLetter = letter - shift;
            
            if (decryptedLetter < 'A' || decryptedLetter > 'Z')
                decryptedLetter += 26;
            
            [result appendFormat:@"%c", decryptedLetter];
        }
        else if (letter == ' ' || (letter >= '0' && letter <= '9')) {
            [result appendFormat:@"%c", letter];
        }
    }
    _openText = [[NSString stringWithString: result] lowercaseString];
}


// frequency analysis
- (int *)letterFrequency:(char)key {
    
    [self decrypt:key];
    static int result[26];
    
    for (char i = 0; i < 26; i++)
        result[i] = 0;
    
    for (int i = 0; i < [_openText length]; i++) {
        char letter = [_openText characterAtIndex:i];
        
        if (letter >= 'a' && letter <= 'z')
            result[letter - 'a']++;
    }
    return result;
}

- (char)guessKeyLetterFrequency {
    
    char bestTip = 'a';
    float actualDifference;
    float smallestDifference = FLT_MAX;
    float expectedFrequency[26] = {8.167, 1.492, 2.782, 4.253, 13.000, 2.228, 2.015, 6.094, 6.966, 0.153, 0.772, 4.025, 2.406, 6.749, 7.507, 1.929, 0.095, 5.987, 6.327, 9.056, 2.758, 0.978, 2.360, 0.150, 1.974, 0.074};
    int * actualFrequency;
    
    for (char i = 0; i < 26; i++) {
        actualDifference = 0.0;
        actualFrequency = [self letterFrequency:'a' + i];
        
        for (char j = 0; j < 26; j++) {
            float letterPercent = ((float)actualFrequency[j] / [_openText length]) * 100;
            actualDifference += abs(letterPercent - expectedFrequency[j]);
        }
        
        if (actualDifference < smallestDifference) {
            smallestDifference = actualDifference;
            bestTip = 'a' + i;
        }
    }
    return bestTip;
}

- (NSString *)letterFrequencyBreak {
    
    [self decrypt:[self guessKeyLetterFrequency]];
    
    return _openText;
}


// real words analysis
- (int)calculateWordsCount:(char)key {
    
    [self decrypt:key];
    int result = 0;
    NSError *error = nil;
    
    for (NSString *word in _mostUsedWords) {
        NSRegularExpression *regex = [NSRegularExpression regularExpressionWithPattern:word options:NSRegularExpressionCaseInsensitive error:&error];
        
        result += [regex numberOfMatchesInString:_openText options:0 range:NSMakeRange(0, [_openText length])];
    }
    return result;
}

- (char)guessKeyWordsCount {
    
    char result = '\0';
    int keyProbability[26];
    
    for (char i = 0; i < 26; i++)
        keyProbability[i] = [self calculateWordsCount:'a' + i];
    
    int bestValue = 0;
    for (char i = 0; i < 26; i++) {
        
        if (keyProbability[i] > bestValue) {
            bestValue = keyProbability[i];
            result = 'a' + i;
        }
    }
    return result;
}

- (NSString *)wordsCountBreak {
    
    [self decrypt:[self guessKeyWordsCount]];
    
    return _openText;
}


//
+ (NSString *)encrypt:(NSString *)string with:(char)key {
    Caesar *c = [[Caesar alloc] init];
    [c setOpenText:string];
    [c encrypt:key];
    
    return [c codedText];
}

+ (NSString *)decrypt:(NSString *)string with:(char)key {
    Caesar *c = [[Caesar alloc] init];
    [c setCodedText:string];
    [c decrypt:key];
    
    return [c openText];
}

+ (NSString *)breakTheCode:(NSString *)string {
    Caesar *c = [[Caesar alloc] init];
    [c setCodedText:string];
    
    return [c letterFrequencyBreak];
}


@end
