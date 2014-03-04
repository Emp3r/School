#include <iostream>
#include <string>

using namespace std;


int main(int argc, const char * argv[])
{
    
    string v("In the C++ programming language, the retez trida is a standard representation for a retez of text.\n"
             "The trida provides some typical retez operations like comparison, concatenation, find and replace.\n");
    
    string s[]={ string("retez"), string("string"), string("trida"), string("class") };
    
    
    for (int i = 0; i < 4; i+=2) {
        for (int j = v.find(s[i], 0); j != v.find("tohle tam neni", 0); j = v.find(s[i], 0)) {
            v.replace(j, s[i].length(), s[i+1]);
            
        }
    }
    
    
    
    cout << v;
    
    return 0;
}

