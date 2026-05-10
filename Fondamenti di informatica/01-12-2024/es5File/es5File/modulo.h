#include <stdio.h>

typedef struct
{
	float val;
	int num;
}scontrino;

int leggi(FILE * fp, scontrino * dest);
int scrivi(FILE * fp, scontrino src);