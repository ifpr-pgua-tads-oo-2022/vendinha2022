package ifpr.pgua.eic.vendinha2022.model.repositories;

import java.time.LocalDateTime;
import java.util.List;

import ifpr.pgua.eic.vendinha2022.model.daos.ClienteDAO;
import ifpr.pgua.eic.vendinha2022.model.daos.ProdutoDAO;
import ifpr.pgua.eic.vendinha2022.model.daos.VendaDAO;
import ifpr.pgua.eic.vendinha2022.model.entities.Cliente;
import ifpr.pgua.eic.vendinha2022.model.entities.ItemVenda;
import ifpr.pgua.eic.vendinha2022.model.entities.Produto;
import ifpr.pgua.eic.vendinha2022.model.entities.Venda;
import ifpr.pgua.eic.vendinha2022.model.results.Result;
import ifpr.pgua.eic.vendinha2022.model.results.SuccessResult;

public class VendaRepository {
    

    private VendaDAO vendaDao;
    private ClienteDAO clienteDao;
    private ProdutoDAO produtoDao;

    public VendaRepository(VendaDAO dao, ClienteDAO clienteDao, ProdutoDAO produtoDao ){
        this.vendaDao = dao;
        this.clienteDao = clienteDao;
        this.produtoDao = produtoDao;
    }

    public Result cadastrar(LocalDateTime dataHora, Cliente cliente, List<ItemVenda> itens){

        if(cliente == null){
            return Result.fail("Cliente inválido!");
        }

        if(dataHora.isAfter(LocalDateTime.now())){
            return Result.fail("Data e hora inválida!");
        }

        if(itens.size() == 0){
            return Result.fail("Nenhum item selecionado!");
        }

        for(ItemVenda item:itens){
            if(item.getQuantidade() > item.getProduto().getQuantidadeEstoque()){
                return Result.fail("Não há a quantidade do produto!!");
            }
        }

        Venda venda = new Venda(cliente,dataHora,itens);

        Result resultado = vendaDao.create(venda);

        if(resultado instanceof SuccessResult){

            for(ItemVenda item:venda.getItens()){

                Produto produtoItem = item.getProduto();
                
                double quantidade = produtoItem.getQuantidadeEstoque() - item.getQuantidade();

                Produto novoItem = new Produto(produtoItem.getId(), produtoItem.getNome(), produtoItem.getDescricao(), produtoItem.getValor(), quantidade);

                produtoDao.update(produtoItem.getId(), novoItem);


            }


        }

        return resultado;



    }

    private Cliente carregaClienteVenda(int id){
        return clienteDao.getClienteFromVenda(id);
    }

    private void carregarProdutoItensVenda(Venda venda){

        for(ItemVenda item:venda.getItens()){
            item.setProduto(produtoDao.getProdutoItem(item.getId()));
        }

    }

    public List<Venda> listar(){

        List<Venda> vendas = vendaDao.getAll();

        for(Venda venda:vendas){
            venda.setCliente(carregaClienteVenda(venda.getId()));

            carregarProdutoItensVenda(venda);

        }

        return vendas;

    }




}
