#include <iostream>
#include <stdlib.h>
#include <stdio.h>
#include <math.h>
#include <typeinfo>
#define _USE_MATH_DEFINES


class Teleso {
protected:
    float vyska;
public:
    Teleso(float v): vyska(v) { ; }
    virtual float objem() =0;
};

class Kvadr: public Teleso {
protected:
    float a, b;
public:
    Kvadr(float a, float b, float c): a(a), b(b), Teleso(c) { ; }
    float objem() { return a * b * vyska; }
};

class TelesoSKruhovouPodstavou: public Teleso {
protected:
    float polomer;
public:
    TelesoSKruhovouPodstavou(float p, float v): polomer(p), Teleso(v) { ; }
    float obsah() { return M_PI * polomer * polomer; }
};

class Valec: public TelesoSKruhovouPodstavou {
public:
    Valec(float p, float v): TelesoSKruhovouPodstavou(p, v) { ; }
    float objem() { return obsah() * vyska; };
};

class Kuzel: public TelesoSKruhovouPodstavou {
public:
    Kuzel(float p, float v): TelesoSKruhovouPodstavou(p, v) { ; }
    float objem() { return (obsah() * vyska) / 3; }
};


int main(int argc, const char * argv[])
{
    Teleso *t[3] = {new Kvadr(5, 6, 7), new Valec(5, 6), new Kuzel(5, 6)};
    
    for(int i = 0; i < 3; ++i) std::cout << typeid(*t[i]).name() << "  " << t[i]->objem() << "\n";
    
    return 0;
}

