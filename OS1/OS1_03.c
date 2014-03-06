#include <stdio.h>
#include <stdlib.h>

// 1.
int aritmeticky_prumer(int a, int b, int c) {
	_asm{
		mov eax, a
		add eax, b
		add eax, c
		
		mov ecx, 3
		cdq
		idiv ecx
	}
}


// 2.
short aritmeticky_prumer2(short a, short b, short c) {
	_asm{
		movsx eax, a
		movsx ebx, b
		movsx ecx, c
		add eax, ebx
		add eax, ecx

		mov edx, 0
		mov ecx, 3
		cdq
		idiv ecx
	}
}

// 3.
int sgn(int i) {
	_asm{
		mov eax, i
		cmp eax, 0
		jz nula
		jg kladne
		jl zaporne
nula:
		mov eax, 0
		jmp konec
kladne:
		mov eax, 1
		jmp konec
zaporne:
		mov eax, -1
		jmp konec
konec:
	}
}

// 4.
short max2(char a, char b) {
	_asm{
		movsx eax, a
		movsx ebx, b
		cmp eax, ebx
		jge konec
		mov eax, ebx
konec:
	}
}

// 5.
short max3(short a, short b, short c) {
	_asm{
		movsx eax, a
		movsx ebx, b
		movsx ecx, c
		cmp eax, ebx
		jge zaver
		mov eax, ebx
zaver:
		cmp eax, ecx
		jge konec
		mov eax, ecx
konec:
	}
}

// 6.
int kladne(int a, int b, int c) {
	_asm{
		mov eax, 1
		cmp a, 0
		jle zaporne
		cmp b, 0
		jle zaporne
		cmp c, 0
		jg konec
zaporne:
		mov eax, 0
konec:
	}
}

// 7.
unsigned int min3(unsigned int a, unsigned int b, unsigned int c) {
	_asm{
		movsx eax, a
		movsx ebx, b
		movsx ecx, c

		cmp eax, ebx
		jbe zaver
		movsx eax, ebx
zaver:
		cmp eax, ecx
		jbe konec
		movsx eax, ecx
konec:
	}
}

int main() { 
	
	printf("prumer -10, -7 a 7: %d\n", aritmeticky_prumer(-10, -7, 7));
	printf("prumer -10, -7 a 7: %d\n", aritmeticky_prumer2(-10, -7, 7));
	printf("signum -10: %d\n", sgn(-10));
	printf("signum 0: %d\n", sgn(0));
	printf("signum 42: %d\n", sgn(42));
	printf("max2 0, 42: %d\n", max2(0, 42));
	printf("max2 100, 42: %d\n", max2(100, 42));
	printf("max2 42, 42: %d\n", max2(42, 42));
	printf("max3 1, 2, 3: %d\n", max3(1, 2, 3));
	printf("max3 4, 2, 3: %d\n", max3(4, 2, 3));
	printf("max3 2, 42, 8: %d\n", max3(2, 42, 8));
	printf("kladne 42, 8, 42: %d\n", kladne(42, 8, 42));
	printf("kladne 42, -8, 42: %d\n", kladne(42, -8, 42));
	printf("min3 1, 2, 3: %d\n", min3(1, 2, 3));
	printf("min3 4, 2, 3: %d\n", min3(4, 2, 3));
	printf("min3 42, 8, 2: %d\n", min3(42, 8, 2));

}