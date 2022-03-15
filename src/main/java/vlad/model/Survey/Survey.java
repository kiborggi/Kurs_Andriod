package vlad.model.Survey;

import vlad.model.Status;

import javax.persistence.*;

@Entity
@Table(name = "survey")
public class Survey  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "description")
    private String description;

    @Column (name = "owner_id")
    private long ownerId;

    @Column(name = "name")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column (name = "status")
    private Status status;

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Survey(){
        this.status=Status.NOT_ACTIVE;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }


    public long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(long ownerId) {
        this.ownerId = ownerId;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    




    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
