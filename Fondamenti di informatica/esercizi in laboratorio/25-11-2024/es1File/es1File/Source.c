#define _CRT_SECURE_NO_WARNINGS
#define _CRT_SECURE_NO_DEPRECATE
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
int main(void) {
	FILE* fp;
	char s[64];
	if ((fp = fopen("prova.txt", "w")) == NULL) {
		exit(1);
	}
	do {
		scanf("%s", s);
		if (strcmp("fine", s) != 0)
			fprintf(fp, "%s ", s);
	} while (strcmp("fine", s) != 0);
	fclose(fp);
	return (0);
}