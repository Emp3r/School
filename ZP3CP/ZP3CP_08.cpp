#include <iostream>


class Zlomek {
    unsigned c, j;
    void nsd() {
        if (c == 0 ) { j = 1; return; }
        unsigned a = c, b = j, r;
        for (;;) {
            r = a % b;
            if (r == 0) { c /= b; j /= b; return; }
            a = b; b = r;
        }
    }
public:
    Zlomek() { }
    Zlomek(unsigned c, unsigned j): c(c), j(j) { nsd(); }
    Zlomek operator * (const Zlomek &z) {
        return Zlomek(c * z.c, j * z.j);
    }
    Zlomek operator + (const Zlomek &z) {
        return Zlomek((c * z.j) + (z.c * j), j * z.j);
    }
    Zlomek operator - (const Zlomek &z) {
        return Zlomek((c * z.j) - (z.c * j), j * z.j);
    }
    Zlomek operator / (const Zlomek &z) {
        return Zlomek(c, j) * Zlomek(z.j, z.c);
    }
    
    
    Zlomek operator + (const unsigned &u) {
        return Zlomek((c + (u * j)), j);
    }
    Zlomek operator - (const unsigned &u) {
        return Zlomek((c - (u * j)), j);
    }
    Zlomek operator * (const unsigned &u) {
        return Zlomek((c * u), j);
    }
    Zlomek operator / (const unsigned &u) {
        return Zlomek((c / u), j);
    }
    
    
    bool operator == (const Zlomek &z) {
        return (c == z.c && j == z.j);
    }
    bool operator != (const Zlomek &z) {
        return (c != z.c && j != z.j);
    }
    
    
    double operator + () const {
        return (double)c / j;
    }
    void operator () () const {
        std::cout << c << '/' << j << "\n";
    }
};

Zlomek operator + (const unsigned &u, const Zlomek &z) {
    return Zlomek(u, 1) + z;
}
Zlomek operator - (const unsigned &u, const Zlomek &z) {
    return Zlomek(u, 1) - z;
}
Zlomek operator * (const unsigned &u, const Zlomek &z) {
    return Zlomek(u, 1) * z;
}
Zlomek operator / (const unsigned &u, const Zlomek &z) {
    return Zlomek(u, 1) / z;
}


int main(int argc, const char * argv[])
{
    Zlomek v1 = (Zlomek(1, 2) - Zlomek(1, 3) + 1) * 5 / (2 + Zlomek(2, 3));
    std::cout << "1: ";
    v1();
    
    Zlomek v2 = (Zlomek(3, 2) - Zlomek(1, 8));
    std::cout << "2: " << +v2 << "\n";
    
    Zlomek z1 = Zlomek(1, 2) - Zlomek(1, 3);
    Zlomek z2 = 2 * Zlomek(1, 12);
    std::cout << "3: " << (z1 == z2) << "\n";
    
    
    
    
    std::cout << "\n";
    return 0;
}

