#include <stdio.h>

// 1. Studenti
typedef char string[10];
typedef struct {short den, mesic, rok;} Datum;
typedef struct {string jmeno, prijmeni; Datum narozen;} Student;

int porovnej_vek(Student s1, Student s2)
{
    if (s1.narozen.rok < s2.narozen.rok) return -1;
    else if (s1.narozen.rok > s2.narozen.rok) return 1;
    else if (s1.narozen.mesic < s2.narozen.mesic) return -1;
    else if (s1.narozen.mesic > s2.narozen.mesic) return 1;
    else if (s1.narozen.den < s2.narozen.den) return -1;
    else if (s1.narozen.den > s2.narozen.den) return 1;
    else return 0;
}

// 2. Zlomky
typedef struct {int citatel, jmenovatel;} Zlomek;   // strukturovaný datový typ Zlomek

int eukl_alg(int a, int b)                          // funkce na Euklidův algoritmus
{
    int r, u, v;
    if (a > b) {
        u = a;
        v = b; }
    else {
        u = b;
        v = a; }
    while (v != 0){
        r = u % v;
        u = v;
        v = r; }
    return u;
}
// 2.1 Součet zlomků (sčítání)
Zlomek soucet(Zlomek z1, Zlomek z2)
{
    Zlomek vysledek;
    int delitel;
    
    vysledek.jmenovatel = z1.jmenovatel * z2.jmenovatel;
    vysledek.citatel = (z1.citatel * z2.jmenovatel) + (z2.citatel * z1.jmenovatel);
    
    delitel = eukl_alg(vysledek.citatel, vysledek.jmenovatel);
    vysledek.citatel = vysledek.citatel / delitel;
    vysledek.jmenovatel = vysledek.jmenovatel / delitel;
    
    return vysledek;
}
// 2.2 Rozdíl zlomků (odčítání)
Zlomek rozdil(Zlomek z1, Zlomek z2) {
    Zlomek vysledek;
    int delitel;
    vysledek.citatel = z1.citatel * z2.jmenovatel - z2.citatel * z1.jmenovatel;
    vysledek.jmenovatel = z1.jmenovatel * z2.jmenovatel;
    delitel = eukl_alg(vysledek.citatel, vysledek.jmenovatel);
    vysledek.citatel = vysledek.citatel / delitel;
    vysledek.jmenovatel = vysledek.jmenovatel / delitel;
    return vysledek;
}
// 2.3 Součin zlomků (násobení)
Zlomek soucin(Zlomek z1, Zlomek z2) {
    Zlomek vysledek;
    int delitel;
    vysledek.citatel = z1.citatel * z2.citatel;
    vysledek.jmenovatel = z1.jmenovatel * z2.jmenovatel;
    delitel = eukl_alg(vysledek.citatel, vysledek.jmenovatel);
    vysledek.citatel = vysledek.citatel / delitel;
    vysledek.jmenovatel = vysledek.jmenovatel / delitel;
    return vysledek;
}
// 2.4 Podíl zlomků (dělení)
Zlomek podil(Zlomek z1, Zlomek z2) {
    Zlomek vysledek;
    int delitel;
    vysledek.citatel = z1.citatel * z2.jmenovatel;
    vysledek.jmenovatel = z1.jmenovatel * z2.citatel;
    delitel = eukl_alg(vysledek.citatel, vysledek.jmenovatel);
    vysledek.citatel = vysledek.citatel / delitel;
    vysledek.jmenovatel = vysledek.jmenovatel / delitel;
    return vysledek;
}

// 3. Historie transakcí (2. úkol od Havrlanta, to samý co 1. Studenti)
typedef struct {double castka, varsymbol; Datum splatnost;} Transakce;
int porovnej_splatnost(Transakce t1, Transakce t2) {
    if (t1.splatnost.rok < t2.splatnost.rok) return -1;
    else if (t1.splatnost.rok > t2.splatnost.rok) return 1;
    else if (t1.splatnost.mesic < t2.splatnost.mesic) return -1;
    else if (t1.splatnost.mesic > t2.splatnost.mesic) return 1;
    else if (t1.splatnost.den < t2.splatnost.den) return -1;
    else if (t1.splatnost.den > t2.splatnost.den) return 1;
    else return 0;
}

int main()
{
    // 1. Studenti
    Student stud1 = {"Bill", "Gates", {28, 9, 1955}};
    Student stud2 = {"Steve", "Jobs", {24, 2, 1955}};
    
    if (porovnej_vek(stud1, stud2) == -1)
        printf("%s %s je starší než %s %s.", stud1.jmeno, stud1.prijmeni, stud2.jmeno, stud2.prijmeni);
    else if (porovnej_vek(stud1, stud2) == 1)
        printf("%s %s je starší než %s %s.", stud2.jmeno, stud2.prijmeni, stud1.jmeno, stud1.prijmeni);
    else
        printf("%s %s a %s %s jsou stejně staří.", stud1.jmeno, stud1.prijmeni, stud2.jmeno, stud2.prijmeni);
    
    if (porovnej_vek(stud1, stud1) == -1)
        printf("\n%s %s je starší než %s %s.", stud1.jmeno, stud1.prijmeni, stud1.jmeno, stud1.prijmeni);
    else if (porovnej_vek(stud1, stud1) == 1)
        printf("\n%s %s je starší než %s %s.", stud1.jmeno, stud1.prijmeni, stud1.jmeno, stud1.prijmeni);
    else
        printf("\n%s %s a %s %s jsou stejně staří.", stud1.jmeno, stud1.prijmeni, stud1.jmeno, stud1.prijmeni);
    
    // 2.1 Součet zlomků
    Zlomek zlom1 = {2, 3};
    Zlomek zlom2 = {-1, 6};
    Zlomek zlom3 = {3, 22};
    Zlomek zlom4 = {1, 33};
    
    printf("\n\nVýsledek součtu zlomků %i/%i a %i/%i je %i/%i.", zlom1.citatel, zlom1.jmenovatel, zlom2.citatel, zlom2.jmenovatel, soucet(zlom1, zlom2).citatel, soucet(zlom1, zlom2).jmenovatel);
    printf("\nVýsledek součtu zlomků %i/%i a %i/%i je %i/%i.", zlom3.citatel, zlom3.jmenovatel, zlom4.citatel, zlom4.jmenovatel, soucet(zlom3, zlom4).citatel, soucet(zlom3, zlom4).jmenovatel);
    // 2.x Ostatní
    Zlomek z1 = {3, 4};
    Zlomek z2 = {5, 3};
    printf("\n\n3/4 + 5/3 = %i/%i", soucet(z1, z2).citatel, soucet(z1, z2).jmenovatel);
    printf("\n3/4 - 5/3 = %i/%i", rozdil(z1, z2).citatel, rozdil(z1, z2).jmenovatel);
    printf("\n3/4 * 5/3 = %i/%i", soucin(z1, z2).citatel, soucin(z1, z2).jmenovatel);
    printf("\n3/4 / 5/3 = %i/%i", podil(z1, z2).citatel, podil(z1, z2).jmenovatel);
    
    // 3. Historie transakcí
    Transakce najem = {11000, 2012125, {1, 12, 2012}};
    Transakce mzda = {29537.32, 201212587, {16, 12, 2012}};
    
    printf("\n\n%i", porovnej_splatnost(najem, mzda));
    printf("\n%i", porovnej_splatnost(mzda, najem));
    printf("\n%i", porovnej_splatnost(mzda, mzda));
    
    return 0;
}