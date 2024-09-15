package guru.springframework.domain;

import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

/**
 * Created by jt on 1/10/17.
 */
@Getter
@Setter
@Document
public class Product {
    @Id
    private ObjectId id;
    private String description;
    private BigDecimal price;
    private String imageUrl;
}
