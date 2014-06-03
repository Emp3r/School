#import <Foundation/Foundation.h>

@interface Vigenere : NSObject

// texts
@property NSString *openText;
@property NSString *codedText;

// methods
- (void)encrypt:(NSString *)key;
- (void)decrypt:(NSString *)key;

- (void)convertCodedText;
- (int *)findDistanceDivisors;
- (int)guessKeyLength;
- (NSArray *)codedTextSplitted;

- (int *)letterFrequency:(char)key from:(NSString *)part;
- (char)guessKeyFor:(NSString *)string;
- (NSString *)guessKeyLetterFrequency;
- (NSString *)letterFrequencyBreak;


+ (NSString *)encrypt:(NSString *)string with:(NSString *)key;
+ (NSString *)decrypt:(NSString *)string with:(NSString *)key;
+ (NSString *)breakTheCode:(NSString *)string;


@end
