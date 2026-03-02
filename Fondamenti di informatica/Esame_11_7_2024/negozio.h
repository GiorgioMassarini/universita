#include "list.h"

Formaggio* leggiFormaggi(char* fileName, int* dim);
list filtraFormaggi(Formaggio* elenco, int dim, char* tipo, float maxPrice);
void stampaFormaggi(list elenco);
//FUNZIONI PER ORDINARE
void ordina(Formaggio* elenco, int dim);
void scambia(Formaggio* a, Formaggio* b);
int compare(Formaggio t1, Formaggio t2); //IMPONE LE CONDIZIONI DELL'ORDINAMENTO
void quickSortR(Formaggio a[], int iniz, int fine);
void quickSort(Formaggio* a, int dim);
// FINE FUNZIONI PER ORDINARE
float controvalore(Formaggio* elenco, int dim, char* tipo);
void stampaControvalori(Formaggio* elenco, int dim);



