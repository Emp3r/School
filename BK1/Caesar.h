#import <Foundation/Foundation.h>

@interface Caesar : NSObject

// texts
@property NSString *openText;
@property NSString *codedText;
@property NSArray *mostUsedWords;

// methods
- (void)encrypt:(char)key;
- (void)decrypt:(char)key;

- (int *)letterFrequency:(char)key;
- (char)guessKeyLetterFrequency;
- (NSString *)letterFrequencyBreak;

- (int)calculateWordsCount:(char)key;
- (char)guessKeyWordsCount;
- (NSString *)wordsCountBreak;


+ (NSString *)encrypt:(NSString *)string with:(char)key;
+ (NSString *)decrypt:(NSString *)string with:(char)key;
+ (NSString *)breakTheCode:(NSString *)string;


@end
