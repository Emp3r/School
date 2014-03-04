#include <stdlib.h>
#include <stdio.h>
#include <iostream>

using namespace std;

class Pole {
    int *pole;
    const int n;
    int pocet;
    
public: Pole(int n): n(n) {
    pole = new int[n];
    pocet = 0;
    };
    ~Pole() {
        free(pole);
    };
    int najit(int c) const;
    bool pridat(int c);
    friend void vypsat(const Pole &);
    friend bool zrusit(Pole &,int c);
};

int Pole::najit(int c) const {
	for(int i = 0; i < n; ++i) {
		if(pole[i] == c) return i;
	}
	return false;
}

bool Pole::pridat(int c) {
	if(pocet > n) {
        return false;
    }
	else {
		pole[pocet++] = c;
		return true;
	}
}

bool zrusit(Pole &p, int c) {
	for(int i = 0; i < p.pocet; ++i) {
		if(p.pole[i] == c) {
			for(int j = i; j < p.pocet; ++j) {
				p.pole[j] = p.pole[j+1];
			}
			p.pocet--;
			return true;
		}
        
	}
	return false;
}

void vypsat(const Pole &p) {
	for(int i = 0; i < p.pocet; ++i) {
		cout << p.pole[i] << ", ";
	}
}


int main(){
    Pole p(6);
	p.pridat(1);
	p.pridat(6);
	p.pridat(2);
	p.pridat(9);
	p.pridat(3);
	p.pridat(5);
    
	vypsat(p);
	
    zrusit(p, 3);
    zrusit(p, 1);
    zrusit(p, 5);
    
	cout << "\n\n";
    
	vypsat(p);
    
}
