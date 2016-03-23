#include <iostream>
#include <fstream>
#include <math.h> //pow
#include <string>


using namespace std;
string convert(string input);
int digits (int i) {
	int digits = 1;
	while (i) {
		i/=10;
		digits++;
	}
}
string to_binary(int n) {
   std::string r;
   while(n!=0) {r=(n%2==0 ?"0":"1")+r; n/=2;}
   return r;
}
int main() {
	string line,result;
	result="";
	ifstream bcd;
	bcd.open("Prvocisla.bcd",ios::binary);
	ofstream out("primarnynum.bin");
	unsigned char c;
	int num=0;
	if (bcd.is_open()) {
		unsigned int result=0;
		while (true)  {
			c=bcd.get(); 
			if (bcd.eof()) break; 
			int second = c & 0xf;
			c >>= 4;
			int first = c & 0xf;
			result+=first;
			if (second == 15) {
				num+=to_binary(result).length();
				if (num > 180) {
					out << endl;
					num=0;
				}
				out << to_binary(result) << " | ";
				num+=3;
				result=0;
			}
			else {
				result*=10;
				result+=second;
				result=result*10;
			}
		}
		
	}
	else {
		cout << "Fail" << endl;
	}
}


