#ifndef _ELEMENT_H
#define _ELEMENT_H

#define _CRT_SECURE_NO_WARNINGS
#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <malloc.h>
#define false 0
#define true 1

typedef struct
{
	char nome[32];
	char tipo[16];
	float prezzo;
	float quantita;
}Formaggio;

typedef Formaggio element;

#endif