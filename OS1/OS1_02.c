#include <stdio.h>
#include <stdlib.h>
#include <math.h>

// 1.
int obvod_obdelnika(int a, int b) {
	_asm{
		mov eax, a
		add eax, b
		imul eax, 2
	}
}

// 2.
int obsah_obdelnika(int a, int b) {
	_asm {
		mov eax, a
		imul eax, b
	}
}

// 3.
int obvod_ctverce(int a) {
	_asm{
		mov eax, a
		imul eax, 4
	}
}

// 4.
int obsah_ctverce(int a) {
	_asm{
		mov eax, a
		imul eax, a
	}
}

// 5. 
int obvod_trojuhelnika(int a, int b, int c) {
	_asm{
		mov eax, a
		add eax, b
		add eax, c
	}
}

// 6.
int obvod_trojuhelnika2(int a) {
	_asm{
		mov eax, a
		imul eax, 3
	}
}

// 7.
int obsah_trojuhelnika(int a, int b) {
	_asm{
		mov eax, a
		imul eax, b
		mov edx, 0
		mov ecx, 2
		div ecx
	}
}

// 8. 
int obsah_trojuhelnika2(int a, int va) {
	_asm{
		mov eax, a
		imul eax, va
		mov edx, 0
		mov ecx, 2
		div ecx
	}
}

// 9.
int objem_krychle(int a) {
	_asm{
		mov eax, a
		imul eax, a
		imul eax, a
	}	
}

// 10.
double obsah_trojuhelniku_heron(int a, int b, int c) {
	int result = 0;
	_asm{
		mov eax, a
		add eax, b
		add eax, c
		mov edx, 0
		mov ecx, 2
		div ecx
		mov ecx, eax

		mov ebx, ecx
		sub ebx, a
		imul eax, ebx
		mov ebx, ecx
		sub ebx, b
		imul eax, ebx
		mov ebx, ecx
		sub ebx, c
		imul eax, ebx

		mov result, eax
	}
	return sqrt((double)result);
}


int main() {
	printf("obdelnik 2 a 3, obvod: %i, obsah: %i\n", obvod_obdelnika(2, 3), obsah_obdelnika(2, 3));
	printf("obdelnik 15 a 6, obvod: %i, obsah: %i\n", obvod_obdelnika(15, 6), obsah_obdelnika(15, 6));
	printf("ctverec 3, obvod: %i, obsah: %i\n", obvod_ctverce(3), obsah_ctverce(3));
	printf("ctverec 42, obvod: %i, obsah: %i\n", obvod_ctverce(42), obsah_ctverce(42));
	printf("trojuhelnik 3, 4 a 4, obvod: %i, obsah: %i\n", obvod_trojuhelnika(3, 4, 4), obsah_trojuhelnika(3, 4));
	printf("rovnostranny trojuhelnik 4, obvod: %i\n", obvod_trojuhelnika2(4));
	printf("trojuhelnik o strane 4 a vysce 5, obsah: %i\n", obsah_trojuhelnika2(4, 5));
	printf("krychle o strane 4, objem: %i\n", objem_krychle(4));
	printf("trojuhelnik 5 6 3 pomoci heronova vzorce: %g\n", obsah_trojuhelniku_heron(5, 6, 3));

	return 0;
}