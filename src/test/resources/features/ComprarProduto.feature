            #language: pt

            Funcionalidade: Comprar Produto
            Esquema do Cenario: Compra com Sucesso
            Dado que acesso o My Demo App
            E verifico o logo e o nome da secao
            E localizo o <produto> que na <rolagem> esta na posicao <num_produto> por <preco>
            Quando clico na imagem do <num_produto>
            Entao na tela do produto verifico o <produto> e o <preco>
            Quando arrasto para cima e clico no botao Add Cart
            Entao na tela do carrinho verifico o <produto> <preco> e <quantidade>
            Exemplos:
            | produto                     | preco     | rolagem | num_produto | quantidade |
            | "Sauce Labs Backpack"       | "$ 29.99" | 0       | 1           | 1          |
            | "Sauce Labs Backpack (red)" | "$ 29.99" | 1       | 2           | 1          |