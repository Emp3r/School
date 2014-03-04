#include <iostream>
#include <vector>
#include <string.h>

using namespace std;

struct Osoba {
private:
    string jmeno, prijmeni;
public:
    Osoba(string j, string p): prijmeni(p), jmeno(j) {}
    string getJmeno() { return jmeno; }
    string getPrijmeni() { return prijmeni; }
    string get() { return jmeno + " " + prijmeni; }
};

bool pridat(vector<Osoba> &v, const char *jmeno, const char *prijmeni) {
    for (int i = 0; i < v.size(); i++) {
        if (v[i].getJmeno() == jmeno && v[i].getPrijmeni() == prijmeni)
            return false;
    }
    v.push_back(* new Osoba(jmeno, prijmeni));
    return true;
}

bool zrusit(vector<Osoba> &v,const char *jmeno, const char *prijmeni) {
    for(vector<Osoba>::iterator it = v.begin(); it != v.end(); ++it) {
        if (it->getJmeno() == jmeno && it->getPrijmeni() == prijmeni) {
            v.erase(it);
            return true;
        }
    }
    return false;
}


int main(int argc, const char * argv[])
{    
    vector<Osoba> vec;
    
    pridat(vec, "Walter", "White");
    pridat(vec, "Jesse", "Pinkman");
    pridat(vec, "Jimmy", "Darmody");
    pridat(vec, "Saul", "Goodman");
    
    zrusit(vec, "Jimmy", "Darmody");
    
    for (int i = 0; i < vec.size(); i++)
        cout << vec[i].get() << "\n";
    
    return 0;
}