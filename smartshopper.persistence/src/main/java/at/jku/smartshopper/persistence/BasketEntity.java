package at.jku.smartshopper.persistence;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class BasketEntity {

	@Id
	private Date insertStamp;
	@ManyToOne
	private UserEntity user;
	@OneToMany(cascade=CascadeType.ALL)
	private List<BasketToArticleEntity> basketToArticle;
//	@ManyToOne(cascade=CascadeType.ALL)
	@ManyToOne
	private ShopEntity shop;

	public BasketEntity() {
		super();
	}

	public BasketEntity(UserEntity user, Date insertStamp,
			List<BasketToArticleEntity> basketToArticle, ShopEntity shop) {
		super();
		this.user = user;
		this.insertStamp = insertStamp;
		this.basketToArticle = basketToArticle;
		this.shop = shop;
	}

	public UserEntity getUser() {
		return user;
	}

	public void setUser(UserEntity user) {
		this.user = user;
	}

	public Date getInsertStamp() {
		return insertStamp;
	}

	public void setInsertStamp(Date insertStamp) {
		this.insertStamp = insertStamp;
	}

	public List<BasketToArticleEntity> getBasketToArticle() {
		return basketToArticle;
	}

	public void setBasketToArticle(List<BasketToArticleEntity> basketToArticle) {
		this.basketToArticle = basketToArticle;
	}

	public ShopEntity getShop() {
		return shop;
	}

	public void setShop(ShopEntity shop) {
		this.shop = shop;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((basketToArticle == null) ? 0 : basketToArticle.hashCode());
		result = prime * result
				+ ((insertStamp == null) ? 0 : insertStamp.hashCode());
		result = prime * result + ((shop == null) ? 0 : shop.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
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
		BasketEntity other = (BasketEntity) obj;
		if (basketToArticle == null) {
			if (other.basketToArticle != null)
				return false;
		} else if (!basketToArticle.equals(other.basketToArticle))
			return false;
		if (insertStamp == null) {
			if (other.insertStamp != null)
				return false;
		} else if (!insertStamp.equals(other.insertStamp))
			return false;
		if (shop == null) {
			if (other.shop != null)
				return false;
		} else if (!shop.equals(other.shop))
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}

}
