#include <iostream>
#include <regex>

using namespace std;

const char *mmm[] = {"led","uno","brez","dub","kvet","cerv",
    "cervc","srp","zar","rij","list","pros"};

const char *mmmm[] = {"leden","unor","brezen","duben","kveten",
    "cerven","cervenec","srpen","zari","rijen","listopad","prosinec"};

const char *datum[] = {" 1. 10. 2012"," 15-2-1950"," 24/7/1976",
    "26 5 1982  ","21.3.2013 ","20032013","19 3 2013","22. led 2010",
    "12.list.2005"," 31.prosinec 1999 ","2000/6/15","2032013",
    "7-9/1974","3.11-1995"," 1. 10 2012","10.2.202",0};


void print(const char *month)
{
    cmatch cm;
    regex r("([\\s]?)([[:d:]]{1,2})(?:[./-]*[[:space:]]*([[:d:]]{1,2}|[[:alpha:]]+)[./-]*[[:space:]]*([[:d:]]+)[[:space:]]*)?");
    regex_match(month, cm, r);
    
    bool vypis = false;
    for (int i = 0; i < 12; i++)
    {
        if (cm[3] == mmm[i] || cm[3] == mmmm[i]) {
            cout << cm[2] << ". " << i + 1 << ". " << cm[4] << endl;
            vypis = true;
        }
    }
    
    if (!vypis)
        cout << cm[2] << ". " << cm[3] << ". " << cm[4] << endl;
}

int main(int argc, const char * argv[])
{
    regex r1("([[:space:]]*)([[:d:]]{1,2})\\.([[:space:]]*)([[:d:]]{1,2})\\.([[:space:]]*)([[:d:]]{4})([[:space:]]*)");
    regex r2("([[:space:]]*)([[:d:]]{1,2})/([[:d:]]{1,2})/([[:d:]]{4})([[:space:]]*)");
    regex r3("([[:space:]]*)([[:d:]]{1,2})-([[:d:]]{1,2})-([[:d:]]{4})([[:space:]]*)");
    regex r4("([[:space:]]*)([[:d:]]{1,2})([[:space:]]+)([[:d:]]{1,2})([[:space:]]+)([[:d:]]{4})([[:space:]]*)");
    regex r5("([[:space:]]*)([[:d:]]{1,2})([[:space:]]*)([[:d:]]{1,2})\\s*([[:d:]]{4})([[:space:]]*)");
    regex r6("([[:space:]]*)([[:d:]]{1,2})\\.([[:space:]]*)\\w{3,8}([[:space:]]+)([[:d:]]{4})([[:space:]]*)");
    
    cmatch cm;
    
    for (int i = 0; i < 16; i++){
        
        if (regex_match(datum[i], cm, r1) ||
            regex_match(datum[i], cm, r2) ||
            regex_match(datum[i], cm, r3) ||
            regex_match(datum[i], cm, r4) ||
            regex_match(datum[i], cm, r5) ||
            regex_match(datum[i], cm, r6)) {
            
            print(datum[i]);
        }
        else {
            cout << "chybne: " << datum[i] << endl;
        }
        
    }
    
    return 0;
}

