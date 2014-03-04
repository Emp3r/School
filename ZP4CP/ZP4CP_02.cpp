#include <iostream>
#include <fstream>

using namespace std;

int main(int argc, const char * argv[])
{
    try {
        ifstream inputFile("uloha.txt");
        ofstream outputFile("formatovany text.txt");
        
        if (inputFile.is_open()) {
            
            string allText, line, word;
            bool mezery = false;
            
            while(!inputFile.eof()) {
                char ch = inputFile.get();
                
                if (ch != ' ') {
                    word += ch;
                    mezery = false;
                }
                else if (!mezery) {
                    
                    if (line.length() + word.length() > 80) {
                        allText += line + "\n";
                        line = word + ' ';
                    }
                    else {
                        line += word + ' ';
                    }
                    
                    word = "";
                    mezery = true;
                    
                }
                if (inputFile.eof()) {
                    allText += line;
                }
            }
            cout << allText << endl;
            outputFile << allText;
        }
        
        outputFile.close();
        inputFile.close();
    }
    catch (const char* s){
        cout << s << endl;
    }
    
    return 0;
}
