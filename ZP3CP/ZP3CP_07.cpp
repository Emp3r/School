#include <iostream>
#include <iostream>
#include <stdlib.h>
#include <stdio.h>
#include <typeinfo>
#include <stdbool.h>
using namespace std;

class Pole {
    const int d, h;
    int *pole;
    unsigned pocet;
    
public:
    Pole(int d, int h): d(d), h(h) {
        pocet = 0;
        pole = new int[h - d + 1];
        
    }
    ~Pole() {
        free(pole);
    }
    
    int * operator [] (int x) {
        if (x >= d && x <= h)
            return &pole[x - d];
        return nullptr;
    }
    
    bool operator += (int x) {
        if (pocet <= h - d + 1) {
            pole[pocet++] = x;
            return true;
        }
        return false;
    }
    
    bool operator += (const Pole &p) {
        if (p.pocet <= (h - d + 1) - pocet) {
            int t = pocet;
            for (int i = 0; i < p.pocet; i++) {
                pole[t+i] = p.pole[i];
                pocet++;
            }
            return true;
        }
        return false;
    }
    
    bool operator -= (int x) {
        for (int i = 0; i < pocet; i++) {
            if (pole[i] == x) {
                for (int j = i; j < pocet; j++) {
                    pole[j] = pole[j+1];
                }
                pocet--;
                return true;
            }
        }
        return false;
    }
    
    bool operator ! () {
        if (pocet == 0)
            return true;
        return false;
    }
    
    int operator + () {
        int max = pole[0];
        for (int i = 0; i < pocet; i++) {
            if (max < pole[i]) {
                max = pole[i];
            }
        }
        return max;
    }
    
    operator unsigned () {
        return pocet;
    }

};


int main(int argc, const char * argv[])
{
    Pole P1(-5, 5);
    Pole P2(0, 7);
    
    P1 += 2;
    P1 += 11;
    P1 += 4;
    
    P2 += 9;
    P2 += 8;
    
    P1 += P2;
    
    P1 += 6;
    P1 -= 11;
    
    for (int i = 0; i < P1; i++) {
        std::cout << *P1[-5 + i] << ", ";
    }
    std::cout << "\nmax: " << +P1;
    
    return 0;
}

