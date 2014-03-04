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
    
    // Porovnavani
    bool operator < (const Zlomek &z) {
        return (c * z.j) < (z.c * j);
    }
    bool operator > (const Zlomek &z) {
        return (c * z.j) > (z.c * j);
    }
    
    
    double operator + () const {
        return (double)c / j;
    }
    void operator () () const {
        std::cout << c << '/' << j << "";
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

// BubbleSort
template<class T>
void bubbleSort(T a[], int n){
	for(int i = 0; i < n - 1; i++){
		for(int j = 0; j < n - i - 1; j++){
			if(a[j + 1] < a[j]){
				T tmp = a[j+1];
				a[j+1] = a[j];
				a[j] = tmp;
			}
		}
	}
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
    
    // Test
    Zlomek pole[10] = {Zlomek(1, 2), Zlomek(2, 3), Zlomek(2, 9), Zlomek(6, 3), Zlomek(1, 9),
                       Zlomek(4, 3), Zlomek(1, 4), Zlomek(2, 3), Zlomek(8, 2), Zlomek(7, 6)};
    
    std::cout << "\n";
    bubbleSort(pole, 10);

    for (int i = 0; i < 10; i++) {
        pole[i]();
        std::cout << ",  ";
    }
    
    std::cout << "\n";
    return 0;
}

