#import <Foundation/Foundation.h>

#import "Caesar.h"
#import "Vigenere.h"

int main(int argc, const char * argv[])
{
    @autoreleasepool {
        
        NSArray *mostUsedEnglishWords = @[@"the", @"be", @"and", @"of", @"a", @"in", @"to", @"have", @"to", @"it", @"i", @"that", @"for", @"you", @"he", @"with", @"on", @"do", @"say", @"this", @"they", @"at", @"but", @"we", @"his", @"from", @"that", @"not", @"by", @"she", @"or", @"as", @"what", @"go", @"their", @"can", @"who", @"get", @"if", @"would", @"her", @"all", @"my", @"make", @"about", @"know", @"will", @"as", @"up", @"one"];
        
        // encrypted with key "joke"
        NSString *vText1 = @"CVO FRBNMWU YJ RGKEL WC EW WXHNDORMSXX EWNIX UKQN ROWRUXIM PI IMAERM AMQRZVIW OXH OZYVROX LRACP YZKCNFC GXBDVXZ K GAMSRP BKONR MLRZN RJAOH RGKEL CB SWS YJ BWH SCVOV DBVSLYKFUS MLJFKGCSBW JTDIA VSW VCDLNF BILSSZNG K QNGCEPS PVXA QSM ROQJBNMWU DLN ZSJN CP LNF CSW OC TACYJ XT RIA TKMCV SWJOM JUSOW RBDS CVO QXBCXNFPMUZOH KOCIVSXX XT DLNWB LXAO XQS QEVSC XRHVI JBN TUCD EAS MSVDYWNR YJ ASPIASXGNG DS CVO FRPVMLOV WCCBC TBYAW OC XQS LMWRSRP CP MBOKG JQMSARSRP HY QLASPUSX XQS QEVS DSDQRIB CX HJFU EMIVX CVOQNG SRLZEHRBQ GQWVH JPEWN UORMSB MMSXXRHI MWTKRCWMMMS XIPZOGC GEMLWNI JPYVCWYR JBN LXK BIUWQMXB WMPVD RNUKXRJOPH OPJNQD E LVSPM OVP LCXGNDDW FVSGQ JSHNC QEVSC KNBOVJZVC JJYMM";
        
        // encrypted with key "ghz"
        NSString *vText2 = @"ZOD BPFKUDXL BOWGKY HY H LKAGUK NL LMIYXVAHTN ZRWGGIDZPB ZLWZ IX AZHTN Z YLQOLR UM COMEKYDTA BGLRGY BOWGKYR HHRKK NT AGK SDZADXZ NL H JKFVUYC OA HY H ROTORL EUYL UM OUSXGSONHAKAHI ZTHZSOATZPNT AGUBFN AGK JHVODX PR KHRE AN AUCKYRZHMJ HMJ PLVSDSLMZ MNX AGXLD ILMZBQOLR OA QKZHYADJ HKR HSZLLVAR ZV AXLZQ PS ZOHY LZXUDJ PS ZOD JLRIYHVAHUU KK JGOMEXL HTKDIOHLMQGIKK MQKUBN MNX AGK PMJLBOWGKYZHSD IPONLQ SHME WDUWKK OZBL SXPDJ AN OTORLLKUS KUBXFOZPNT ZBNLLKZ SNHS GYD KZRKUSOHKRF UONDTLQK JHVODXZ EXPDJYHIO JGZHYRH CHR ZOD LPQYA SU WTHSHYO Z MLMKYZR TDZONJ VE JLBOWGKYHTN Z BPFKUDXL BOWGKY";
        
        
        NSFileHandle *standartInputHandler = [NSFileHandle fileHandleWithStandardInput];
        
        while (true) {
            
            NSLog(@"type 'c' for Caesar cipher, 'v' for Vigenere cipher or 'q' to quit: ");
            NSData *inputData = [standartInputHandler availableData];
            NSString *inputString = [[NSString alloc] initWithData:inputData encoding:NSUTF8StringEncoding];
            
            switch ([inputString characterAtIndex:0]) {
                case 'c': {
                    
                    NSLog(@"type text encrypted in Caesar cipher: ");
                    inputData = [standartInputHandler availableData];
                    inputString = [[NSString alloc] initWithData:inputData encoding:NSUTF8StringEncoding];
                    
                    Caesar *c = [[Caesar alloc] init];
                    [c setCodedText:[inputString substringToIndex:[inputString length] - 1]];
                    
                    NSLog(@"guessed key: '%c'", [c guessKeyLetterFrequency]);
                    NSLog(@"decrypted text: %@", [c letterFrequencyBreak]);
                    
                    break;
                }
                case 'v': {
                    
                    NSLog(@"type text encrypted in Vigenere cipher: ");
                    inputData = [standartInputHandler availableData];
                    inputString = [[NSString alloc] initWithData:inputData encoding:NSUTF8StringEncoding];
                    
                    Vigenere *v = [[Vigenere alloc] init];
                    [v setCodedText:[inputString substringToIndex:[inputString length] - 1]];
                    
                    NSLog(@"guessed key: '%@'", [v guessKeyLetterFrequency]);
                    NSLog(@"decrypted text: %@", [v letterFrequencyBreak]);
                    
                    break;
                }
                case 'q': {
                    return 0;
                }
                default: {
                    break;
                }
            }
        }
        
    }
    return 0;
}
