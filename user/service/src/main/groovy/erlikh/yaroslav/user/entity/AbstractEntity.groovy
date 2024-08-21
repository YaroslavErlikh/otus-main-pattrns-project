package erlikh.yaroslav.user.entity

import jakarta.persistence.Column
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.MappedSuperclass

@MappedSuperclass
abstract class AbstractEntity<T extends Serializable> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private T id

    T getId() {
        return id
    }

    void setId(T id) {
        this.id = id
    }

    @Override
    String toString() {
        return this.getClass().getSimpleName() + "(id=" + id + ")"
    }

    @Override
    boolean equals(Object other) {
        if (this == other) {
            return true
        }
        if (!(other instanceof AbstractEntity)) {
            return false
        }
        AbstractEntity<? extends Serializable> that = (AbstractEntity<? extends Serializable>) other
        return id != null && id.equals(that.id)
    }

    @Override
    int hashCode() {
        return 31
    }
}
