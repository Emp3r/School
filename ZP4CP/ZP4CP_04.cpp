#include <iostream>
#include <list>

using namespace std;

struct Osoba {
private:
    string jmeno, prijmeni;
public:
    Osoba(string j, string p): jmeno(j), prijmeni(p) {}
    string getJmeno() { return jmeno; }
    string getPrijmeni() const { return prijmeni; }
    string get() { return jmeno + " " + prijmeni; }
    
    bool operator == (Osoba o) {
        return ((jmeno == o.getJmeno()) && (prijmeni == o.getPrijmeni()) );
    }
};

bool obsahuje(list<Osoba> &l, string jmeno, string prijmeni) {
    return !(find(l.begin(), l.end(), Osoba(jmeno, prijmeni)) == l.end());
}

bool pridat(list<Osoba> &l, string jmeno, string prijmeni) {
    if ( obsahuje(l, jmeno, prijmeni) )
        return false;
    
    l.emplace_back(jmeno, prijmeni);
    l.sort([](const Osoba & a, const Osoba & b) {
        // setrid podle prijmeni
        return a.getPrijmeni() < b.getPrijmeni();
    });
    return true;
}

bool zrusit(list<Osoba> &l, string jmeno, string prijmeni) {
    auto nalezenyPrvek = find(l.begin(), l.end(), Osoba(jmeno, prijmeni));
    
    if (nalezenyPrvek != l.end()) {
        l.erase(nalezenyPrvek);
        return true;
    }
    return false;
}


int main(int argc, const char * argv[])
{
    list<Osoba> l;
    pridat(l, "Steve", "Jobs");
    pridat(l, "Steve", "Wozniak");
    pridat(l, "Steve", "Ballmer");
    pridat(l, "Jony", "Ive");
    
    cout << "zrusit Ballmera: " << zrusit(l, "Steve", "Ballmer");
    cout << ", zrusit Waltera: " << zrusit(l, "Walter", "White") << endl;
    cout << "pridat Tima: " << pridat(l, "Tim", "Cook") << endl << endl;
    
    cout << "seznam setrizeny podle prijmeni:" << endl;
    for (Osoba o : l)
        cout << o.get() << endl;
        
    return 0;
}
