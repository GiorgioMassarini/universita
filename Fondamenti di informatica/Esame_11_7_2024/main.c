#include "negozio.h"

int main()
{
	Formaggio* elenco;
	list elencoFiltrato;
	elencoFiltrato = emptylist();
	int dim;
	int i = 0;
	elenco = leggiFormaggi("banco.txt", &dim);
	printf("---------BANCONE---------\n");
	for (i = 0; i < dim; i++)
	{
		printf("%s %s %.2f %.2f \n", elenco[i].nome, elenco[i].tipo, elenco[i].prezzo, elenco[i].quantita);
	}
	elencoFiltrato = filtraFormaggi(elenco, dim, "erborinato", 0.00);
	printf("\n\n--------FORMAGGI FILTRATI----------\n");
	stampaFormaggi(elencoFiltrato);
	ordina(elenco, dim);
	printf("\n\n---------BANCONE ORDINATO---------\n");
	for (i = 0; i < dim; i++)
	{
		printf("%s %s %.2f %.2f \n", elenco[i].nome, elenco[i].tipo, elenco[i].prezzo, elenco[i].quantita);
	}
	printf("\n\n---------CONTROVALORI---------\n");
	stampaControvalori(elenco, dim);
	freelist(elencoFiltrato);
	free(elenco);
}