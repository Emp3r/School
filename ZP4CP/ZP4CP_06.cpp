#include <iostream>
#include <map>

using namespace std;

typedef pair<int, char> pairKey;

struct Prechod { int soucasnyStav; char znak; int nasledujiciStav; };

const Prechod ABBA[] = {{1,'A',2}, {1,'B',1}, {2,'A',2}, {2,'B',3},
                        {3,'A',2}, {3,'B',4}, {4,'A',-5}, {4,'B',1},
                        {-5,'A',-5}, {-5,'B',-5}, {0,0,0}};

bool najdi(map<pair<int, char>, int> automat, const char* str)
{
    int aktualniStav = 1;
    
    for (int i = 0; str[i] != 0; i++) {
        aktualniStav = automat[ pairKey(aktualniStav, str[i]) ];
    }
    return (aktualniStav < 0);
}

int main(int argc, const char * argv[])
{
    const char *vstup1 = "ABBBAABBABABB";
    const char *vstup2 = "ABABAAABBBAA";
    
    map<pairKey, int> automat;
    
    for (const Prechod &prechod : ABBA)
        automat[ pairKey(prechod.soucasnyStav, prechod.znak) ] = prechod.nasledujiciStav;
    
    cout << vstup1 << '\t' << najdi(automat, vstup1) << endl;
    cout << vstup2 << '\t' << najdi(automat, vstup2) << endl;
    
    return 0;
}
