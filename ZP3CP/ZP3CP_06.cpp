#include <iostream>
#include <stdlib.h>
#include <stdio.h>
#include <typeinfo>
#include <stdbool.h>

class KO {
    
public:

    unsigned a;
    static unsigned p;
    
    KO(int _a): a(_a % p) { ; }
    
    KO & operator = (KO & k) {
        a = k.a;
        return *this;
    }
    
    KO & operator = (unsigned _a) {
        a = (_a % p);
        return *this;
    }
    
    KO & operator += (KO & k) {
        a = ((a + k.a) % p);
        return *this;
    }
    
    KO & operator += (unsigned _a) {
        a = ((a + (_a % p)) % p);
        return *this;
    }
    
    KO & operator *= (KO & k) {
        a = ((a * k.a) % p);
        return *this;
    }
    
    KO & operator *= (unsigned _a) {
        a = ((a * (_a % p)) % p);
        return *this;
    }
    
    bool operator ! () {
        return (!a);
    }
    
    bool operator == (KO & k) {
        return (a == k.a);
    }
    
    bool operator == (unsigned _a) {
        return (a == (_a % p));
    }
    
    bool operator != (KO & k) {
        return (a != k.a);
    }
    
    bool operator != (unsigned _a) {
        return (a != (_a % p));
    }
    
    operator unsigned () {
        return a;
    }
    
};

unsigned KO::p = 3;

bool operator == (unsigned _a, KO & k) {
	return ((_a % KO::p) == k.a);
}

bool operator != (unsigned _a, KO & k) {
	return ((_a % KO::p) != k.a);
}



int main(int argc, const char * argv[])
{
    KO a(2);
    KO b(2);
    KO c(2);
    
    b *= 2;
    
    b += 1;
    
    a += b;
    
    std::cout << (2u == b) << "\n" << "\n";
    std::cout << true << "\n";
    std::cout << !true << "\n";
    
    return 0;
}

