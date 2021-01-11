package view;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import controller.CategoryController;
import controller.ProductController;
import model.Category;
import model.Product;


public class ProductCategoryFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	private JLabel labelNome, labelDescricao, labelCategory;
	private JTextField textoNome, textoDescricao;
	private JComboBox<Category> comboCategory;
	private JButton botaoSalvar, botaoEditar, botaoLimpar, botarApagar;
	private JTable tabela;
	private DefaultTableModel modelo;
	private ProductController ProductController;
	private CategoryController CategoryController;

	public ProductCategoryFrame(){
		super("Products");
		Container container = getContentPane();
		setLayout(null);

		this.CategoryController = new CategoryController();
		this.ProductController = new ProductController();

		labelNome = new JLabel("Nome do Product");
		labelDescricao = new JLabel("Descrição do Product");
		labelCategory = new JLabel("Category do Product");

		labelNome.setBounds(10, 10, 240, 15);
		labelDescricao.setBounds(10, 50, 240, 15);
		labelCategory.setBounds(10, 90, 240, 15);

		labelNome.setForeground(Color.BLACK);
		labelDescricao.setForeground(Color.BLACK);
		labelCategory.setForeground(Color.BLACK);

		container.add(labelNome);
		container.add(labelDescricao);
		container.add(labelCategory);

		textoNome = new JTextField();
		textoDescricao = new JTextField();
		comboCategory = new JComboBox<Category>();

		comboCategory.addItem(new Category(0, "Selecione"));
		List<Category> Categorys = this.listCategory();
		for (Category Category : Categorys) {
			comboCategory.addItem(Category);
		}

		textoNome.setBounds(10, 25, 265, 20);
		textoDescricao.setBounds(10, 65, 265, 20);
		comboCategory.setBounds(10, 105, 265, 20);

		container.add(textoNome);
		container.add(textoDescricao);
		container.add(comboCategory);

		botaoSalvar = new JButton("Salvar");
		botaoLimpar = new JButton("Limpar");

		botaoSalvar.setBounds(10, 145, 80, 20);
		botaoLimpar.setBounds(100, 145, 80, 20);

		container.add(botaoSalvar);
		container.add(botaoLimpar);

		tabela = new JTable();
		modelo = (DefaultTableModel) tabela.getModel();

		modelo.addColumn("Identificador do Produto");
		modelo.addColumn("Nome do Produto");
		modelo.addColumn("Descrição do Produto");

		preencherTabela();

		tabela.setBounds(10, 185, 760, 300);
		container.add(tabela);

		botarApagar = new JButton("Excluir");
		botaoEditar = new JButton("Alterar");

		botarApagar.setBounds(10, 500, 80, 20);
		botaoEditar.setBounds(100, 500, 80, 20);

		container.add(botarApagar);
		container.add(botaoEditar);

		setSize(800, 600);
		setVisible(true);
		setLocationRelativeTo(null);

		botaoSalvar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				salvar();
				limparTabela();
				preencherTabela();
			}
		});

		botaoLimpar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				limpar();
			}
		});

		botarApagar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				deletar();
				limparTabela();
				preencherTabela();
			}
		});

		botaoEditar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				alterar();
				limparTabela();
				preencherTabela();
			}
		});
	}

	private void limparTabela() {
		modelo.getDataVector().clear();
	}

	private void alterar() {
		Object objetoDaLinha = (Object) modelo.getValueAt(tabela.getSelectedRow(), tabela.getSelectedColumn());
		if (objetoDaLinha instanceof Integer) {
			Integer id = (Integer) objetoDaLinha;
			String nome = (String) modelo.getValueAt(tabela.getSelectedRow(), 1);
			String descricao = (String) modelo.getValueAt(tabela.getSelectedRow(), 2);
			this.ProductController.alter(nome, descricao, id);
		} else {
			JOptionPane.showMessageDialog(this, "Por favor, selecionar o ID");
		}
	}

	private void deletar() {
		Object objetoDaLinha = (Object) modelo.getValueAt(tabela.getSelectedRow(), tabela.getSelectedColumn());
		if (objetoDaLinha instanceof Integer) {
			Integer id = (Integer) objetoDaLinha;
			this.ProductController.delete(id);
			modelo.removeRow(tabela.getSelectedRow());
			JOptionPane.showMessageDialog(this, "Item excluído com sucesso!");
		} else {
			JOptionPane.showMessageDialog(this, "Por favor, selecionar o ID");
		}
	}

	private void preencherTabela() {
		List<Product> Products = listProduct();
		try {
			for (Product Product : Products) {
				modelo.addRow(new Object[] { Product.getId(), Product.getName(), Product.getDescription() });
			}
		} catch (Exception e) {
			throw e;
		}
	}

	private List<Category> listCategory() {
		return this.CategoryController.list();
	}

	private void salvar() {
		if (!textoNome.getText().equals("") && !textoDescricao.getText().equals("")) {
			Product Product = new Product(textoNome.getText(), textoDescricao.getText());
			Category Category = (Category) comboCategory.getSelectedItem();
			Product.setCategoryId(Category.getId());
			this.ProductController.save(Product);
			JOptionPane.showMessageDialog(this, "Salvo com sucesso!");
			this.limpar();
		} else {
			JOptionPane.showMessageDialog(this, "Nome e Descrição devem ser informados.");
		}
	}

	private List<Product> listProduct() {
		return this.ProductController.list();
	}

	private void limpar() {
		this.textoNome.setText("");
		this.textoDescricao.setText("");
		this.comboCategory.setSelectedIndex(0);
	}
}
