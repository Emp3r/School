#include <iostream>
#include <set>

using namespace std;

enum Mince { M1=1, M2=2, M5=5, M10=10 ,M20=20 ,M50=50 };

const Mince vlozit[] = { M5,M2,M5,M1,M50,M2,M5,M5,M5,M10,M50,M1,M5,M2,M1,M50,M5 };
unsigned vlozitPocet = sizeof(vlozit) / sizeof(*vlozit);

const Mince odebrat[] = { M50, M5, M5, M5, M10 };
unsigned odebratPocet = sizeof(odebrat) / sizeof(*odebrat);

int main(int argc, const char * argv[])
{
    multiset<Mince> mm;
    
    // pridat
    for (const Mince &m : vlozit)
         mm.insert(m);
    
    cout << "M1   " << mm.count(Mince(M1)) << "x\n";
    cout << "M2   " << mm.count(Mince(M2)) << "x\n";
    cout << "M5   " << mm.count(Mince(M5)) << "x\n";
    cout << "M10  " << mm.count(Mince(M10)) << "x\n";
    cout << "M20  " << mm.count(Mince(M20)) << "x\n";
    cout << "M50  " << mm.count(Mince(M50)) << "x\n\n";
    
    // odebrat
    for (const Mince &m : odebrat)
        mm.erase(mm.find(m));
    
    cout << "M1   " << mm.count(Mince(M1)) << "x\n";
    cout << "M2   " << mm.count(Mince(M2)) << "x\n";
    cout << "M5   " << mm.count(Mince(M5)) << "x\n";
    cout << "M10  " << mm.count(Mince(M10)) << "x\n";
    cout << "M20  " << mm.count(Mince(M20)) << "x\n";
    cout << "M50  " << mm.count(Mince(M50)) << "x\n";
    
    return 0;
}
