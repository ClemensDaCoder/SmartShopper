//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2013.05.24 at 04:39:34 PM MESZ 
//


package at.jku.smartshopper.backend;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the at.jku.smartshopper.backend package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _Article_QNAME = new QName("http://backend.smartshopper.jku.at/", "article");
    private final static QName _BasketRow_QNAME = new QName("http://backend.smartshopper.jku.at/", "basketRow");
    private final static QName _Shop_QNAME = new QName("http://backend.smartshopper.jku.at/", "shop");
    private final static QName _User_QNAME = new QName("http://backend.smartshopper.jku.at/", "user");
    private final static QName _BasketList_QNAME = new QName("http://backend.smartshopper.jku.at/", "basketList");
    private final static QName _Basket_QNAME = new QName("http://backend.smartshopper.jku.at/", "basket");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: at.jku.smartshopper.backend
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link User }
     * 
     */
    public User createUser() {
        return new User();
    }

    /**
     * Create an instance of {@link Shop }
     * 
     */
    public Shop createShop() {
        return new Shop();
    }

    /**
     * Create an instance of {@link Basket }
     * 
     */
    public Basket createBasket() {
        return new Basket();
    }

    /**
     * Create an instance of {@link BasketList }
     * 
     */
    public BasketList createBasketList() {
        return new BasketList();
    }

    /**
     * Create an instance of {@link Article }
     * 
     */
    public Article createArticle() {
        return new Article();
    }

    /**
     * Create an instance of {@link BasketRow }
     * 
     */
    public BasketRow createBasketRow() {
        return new BasketRow();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Article }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://backend.smartshopper.jku.at/", name = "article")
    public JAXBElement<Article> createArticle(Article value) {
        return new JAXBElement<Article>(_Article_QNAME, Article.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BasketRow }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://backend.smartshopper.jku.at/", name = "basketRow")
    public JAXBElement<BasketRow> createBasketRow(BasketRow value) {
        return new JAXBElement<BasketRow>(_BasketRow_QNAME, BasketRow.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Shop }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://backend.smartshopper.jku.at/", name = "shop")
    public JAXBElement<Shop> createShop(Shop value) {
        return new JAXBElement<Shop>(_Shop_QNAME, Shop.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link User }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://backend.smartshopper.jku.at/", name = "user")
    public JAXBElement<User> createUser(User value) {
        return new JAXBElement<User>(_User_QNAME, User.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BasketList }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://backend.smartshopper.jku.at/", name = "basketList")
    public JAXBElement<BasketList> createBasketList(BasketList value) {
        return new JAXBElement<BasketList>(_BasketList_QNAME, BasketList.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Basket }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://backend.smartshopper.jku.at/", name = "basket")
    public JAXBElement<Basket> createBasket(Basket value) {
        return new JAXBElement<Basket>(_Basket_QNAME, Basket.class, null, value);
    }

}
