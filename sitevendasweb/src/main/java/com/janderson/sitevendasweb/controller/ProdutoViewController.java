package com.janderson.sitevendasweb.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.janderson.sitevendasweb.entity.ItemPedido;
import com.janderson.sitevendasweb.entity.Produto;
import com.janderson.sitevendasweb.repository.ItemPedidoRepository;
import com.janderson.sitevendasweb.service.ProdutoService;

@Controller
public class ProdutoViewController {

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private ItemPedidoRepository itemPedidoRepository;


    @GetMapping("/admin/produtos")
    public String listarProdutos(Model model) {

        model.addAttribute("produtos", produtoService.listarProdutos());

        return "admin/produtos";
    }


    @GetMapping("/admin/produtos/novo")
    public String novoProduto(Model model) {

        model.addAttribute("produto", new Produto());

        return "admin/produto-form";
    }


    @PostMapping("/admin/produtos/salvar")
    public String salvarProduto(
            Produto produto,

            @RequestParam(
                value = "imagemPrincipalArquivo",
                required = false
            )
            MultipartFile imagemPrincipalArquivo,

            @RequestParam(
                value = "imagensAdicionaisArquivos",
                required = false
            )
            MultipartFile[] imagensAdicionaisArquivos,

            @RequestParam(
                value = "removerImagens",
                required = false
            )
            List<String> removerImagens

    ) throws IOException {
        /*
         * IMPORTANTE:
         * src/main/resources/static/img
         *
         * Como estamos rodando localmente pelo Eclipse,
         * vamos salvar as imagens diretamente nessa pasta.
         */
    	Path pastaImagens = Paths.get("uploads");

        Files.createDirectories(pastaImagens);


        /*
         * Se estamos EDITANDO um produto,
         * buscamos o produto antigo para preservar
         * imagem principal e imagens adicionais.
         */
        Produto produtoExistente = null;

        if (produto.getId() != null) {

            produtoExistente =
                    produtoService.buscarProdutoPorId(produto.getId());
        }


        /*
         * ============================
         * IMAGEM PRINCIPAL
         * ============================
         */

        if (imagemPrincipalArquivo != null
                && !imagemPrincipalArquivo.isEmpty()) {

            String nomeArquivo =
                    gerarNomeArquivo(imagemPrincipalArquivo);

            Path destino =
                    pastaImagens.resolve(nomeArquivo);

            Files.copy(
                    imagemPrincipalArquivo.getInputStream(),
                    destino,
                    StandardCopyOption.REPLACE_EXISTING
            );

            produto.setImagemUrl(
                    "/uploads/" + nomeArquivo
            );

        } else if (produtoExistente != null) {

            /*
             * Se o usuário não escolheu uma nova foto,
             * mantém a foto principal antiga.
             */
            produto.setImagemUrl(
                    produtoExistente.getImagemUrl()
            );
        }


        /*
         * ============================
         * IMAGENS ADICIONAIS
         * ============================
         */

        List<String> imagens = new ArrayList<>();


        /*
         * Mantém imagens adicionais já existentes
         * quando estamos editando.
         */
        if (produtoExistente != null
                && produtoExistente.getImagens() != null) {

            imagens.addAll(
                    produtoExistente.getImagens()
            );
        }
        
     // REMOVE IMAGENS MARCADAS NO ADMIN
        
        if (removerImagens != null) {

            for (String imagemRemover : removerImagens) {

                if (imagemRemover == null || imagemRemover.isBlank()) {
                    continue;
                }

                imagens.remove(imagemRemover);

                if (imagemRemover.startsWith("/uploads/")) {

                    String nomeArquivo =
                            imagemRemover.replace("/uploads/", "");

                    Path arquivo =
                            Paths.get("uploads").resolve(nomeArquivo);

                    Files.deleteIfExists(arquivo);
                }
            }
        }


        /*
         * Adiciona as novas imagens escolhidas.
         */
        if (imagensAdicionaisArquivos != null) {

            for (MultipartFile arquivo
                    : imagensAdicionaisArquivos) {

                if (arquivo == null
                        || arquivo.isEmpty()) {

                    continue;
                }

                String nomeArquivo =
                        gerarNomeArquivo(arquivo);

                Path destino =
                        pastaImagens.resolve(nomeArquivo);

                Files.copy(
                        arquivo.getInputStream(),
                        destino,
                        StandardCopyOption.REPLACE_EXISTING
                );

                imagens.add(
                        "/uploads/" + nomeArquivo
                );
            }
        }

        produto.setImagens(imagens);


        /*
         * Finalmente salva o produto no banco.
         */
        produtoService.salvarProduto(produto);

        return "redirect:/admin/produtos";
    }


    /*
     * Gera um nome único para evitar sobrescrever
     * fotos com o mesmo nome.
     *
     * Exemplo:
     *
     * arquivo original:
     * tapete.jpg
     *
     * salvo como:
     * 8d3f2a...-tapete.jpg
     */
    private String gerarNomeArquivo(
            MultipartFile arquivo) {

        String nomeOriginal =
                arquivo.getOriginalFilename();

        if (nomeOriginal == null
                || nomeOriginal.isBlank()) {

            nomeOriginal = "imagem.jpg";
        }

        /*
         * Remove caracteres problemáticos do nome.
         */
        nomeOriginal =
                nomeOriginal.replaceAll(
                        "[^a-zA-Z0-9._-]",
                        "_"
                );

        return UUID.randomUUID()
                + "-"
                + nomeOriginal;
    }


    @GetMapping("/admin/produtos/editar/{id}")
    public String editarProduto(
            @PathVariable Long id,
            Model model) {

        Produto produto =
                produtoService.buscarProdutoPorId(id);

        model.addAttribute("produto", produto);

        return "admin/produto-form";
    }


    @PostMapping("/admin/produtos/{id}/excluir")
    public String excluirProduto(
            @PathVariable Long id,
            Model model,
            RedirectAttributes redirectAttributes) {

        try {

            produtoService.deletarProduto(id);

            redirectAttributes.addFlashAttribute(
                    "sucesso",
                    "Produto excluído com sucesso!"
            );

            return "redirect:/admin/produtos";


        } catch (RuntimeException e) {

            System.out.println("Produto ID: " + id);

            System.out.println(
                    "ERRO AO EXCLUIR PRODUTO:"
            );

            System.out.println(e.getMessage());

            List<ItemPedido> itens =
                    itemPedidoRepository.findByProduto_Id(id);

            System.out.println(
                    "TOTAL DE ITENS ENCONTRADOS: "
                    + itens.size()
            );

            for (ItemPedido item : itens) {

                System.out.println(
                        "ITEM ID: "
                        + item.getId()
                );

                if (item.getPedido() != null) {

                    System.out.println(
                            "PEDIDO ID: "
                            + item.getPedido().getId()
                    );

                } else {

                    System.out.println(
                            "PEDIDO: SEM PEDIDO"
                    );
                }
            }

            model.addAttribute(
                    "pedidos",
                    itens
            );

            return "admin/produto-relacionado";
        }
    }


    @GetMapping("/admin/produtos/ativar/{id}")
    public String ativarProduto(
            @PathVariable Long id) {

        Produto produto =
                produtoService.buscarProdutoPorId(id);

        if (produto != null) {

            produto.setAtivo(true);

            produtoService.salvarProduto(produto);
        }

        return "redirect:/admin/produtos";
    }


    @GetMapping("/admin/produtos/desativar/{id}")
    public String desativarProduto(
            @PathVariable Long id) {

        Produto produto =
                produtoService.buscarProdutoPorId(id);

        if (produto != null) {

            produto.setAtivo(false);

            produtoService.salvarProduto(produto);
        }

        return "redirect:/admin/produtos";
    }


    @GetMapping("/produto/{id}")
    public String detalheProduto(
            @PathVariable Long id,
            Model model) {

        Produto produto =
                produtoService.buscarProdutoPorId(id);

        model.addAttribute(
                "produto",
                produto
        );

        return "produto-detalhe";
    }

}