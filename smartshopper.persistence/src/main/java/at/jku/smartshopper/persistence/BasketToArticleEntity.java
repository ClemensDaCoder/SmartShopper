package at.jku.smartshopper.persistence;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class BasketToArticleEntity {

	@GeneratedValue
	@Id
	private Long id;
	private Integer amount;
	@ManyToOne
	private BasketEntity basket;
	@ManyToOne
	private ArticleEntity article;

	public BasketToArticleEntity(Long id, Integer amount, BasketEntity basket,
			ArticleEntity article) {
		super();
		this.id = id;
		this.amount = amount;
		this.basket = basket;
		this.article = article;
	}

	public BasketToArticleEntity() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public BasketEntity getBasket() {
		return basket;
	}

	public void setBasket(BasketEntity basket) {
		this.basket = basket;
	}

	public ArticleEntity getArticle() {
		return article;
	}

	public void setArticle(ArticleEntity article) {
		this.article = article;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((amount == null) ? 0 : amount.hashCode());
		result = prime * result + ((article == null) ? 0 : article.hashCode());
		result = prime * result + ((basket == null) ? 0 : basket.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BasketToArticleEntity other = (BasketToArticleEntity) obj;
		if (amount == null) {
			if (other.amount != null)
				return false;
		} else if (!amount.equals(other.amount))
			return false;
		if (article == null) {
			if (other.article != null)
				return false;
		} else if (!article.equals(other.article))
			return false;
		if (basket == null) {
			if (other.basket != null)
				return false;
		} else if (!basket.equals(other.basket))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
