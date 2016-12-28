package com.excilys.formation.model;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.*;

import com.excilys.formation.converter.LocalDateConverter;


/**
 * Computer entity.
 * @author kfuster
 *
 */
@Entity
@Table(name="computer")
public final class Computer implements Serializable {
    private static final long serialVersionUID = -7424514145722416760L;
    // ######### ATTRIBUTES #########
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String name;
    @Column
    @Convert(converter = LocalDateConverter.class)
    private LocalDate introduced;
    @Column
    @Convert(converter = LocalDateConverter.class)
    private LocalDate discontinued;
    
    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    /**
     * Default constructor.
     */
    public Computer() {
    }

    /**
     * Computer constructor, used by ComputerBuilder.
     * @param pName the computer's name
     */
    private Computer(String pName) {
        name = pName;
    }

    // ######### GETTERS/SETTERS #########
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getIntroduced() {
        return introduced;
    }

    public void setIntroduced(LocalDate introduced) {
        this.introduced = introduced;
    }

    public LocalDate getDiscontinued() {
        return discontinued;
    }

    public void setDiscontinued(LocalDate discontinued) {
        this.discontinued = discontinued;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    // ######### METHODS #########
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((company == null) ? 0 : company.hashCode());
        result = prime * result + ((discontinued == null) ? 0 : discontinued.hashCode());
        result = (int) (prime * result + id);
        result = prime * result + ((introduced == null) ? 0 : introduced.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Computer other = (Computer) obj;
        if (company == null) {
            if (other.company != null) {
                return false;
            }
        } else if (!company.equals(other.company)) {
            return false;
        }
        if (discontinued == null) {
            if (other.discontinued != null) {
                return false;
            }
        } else if (!discontinued.equals(other.discontinued)) {
            return false;
        }
        if (id != other.id) {
            return false;
        }
        if (introduced == null) {
            if (other.introduced != null) {
                return false;
            }
        } else if (!introduced.equals(other.introduced)) {
            return false;
        }
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!name.equals(other.name)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return new StringBuilder().append("Computer [id=").append(id).append(", name=").append(name)
                .append(", introduced=").append(introduced).append(", discontinued=").append(discontinued)
                .append(", company=").append(company).append("]").toString();
    }

    public static class ComputerBuilder {
        private Long id;
        private String name;
        private LocalDate introduced;
        private LocalDate discontinued;
        private Company company;

        /**
         * ComputerBuilder constructor.
         * @param pName the computer's name
         */
        public ComputerBuilder(String pName) {
            name = pName;
        }

        /**
         * Setter for the ComputerBuilder's company.
         * @param pCompany the Computer's Company
         * @return the ComputerBuilder
         */
        public ComputerBuilder company(Company pCompany) {
            company = pCompany;
            return this;
        }

        /**
         * Setter for the ComputerBuilder's id.
         * @param pId the Computer's Id
         * @return the ComputerBuilder
         */
        public ComputerBuilder id(Long pId) {
            id = pId;
            return this;
        }

        /**
         * Setter for the ComputerBuilder's introduced date.
         * @param pDate the Computer's introduced date
         * @return the ComputerBuilder
         */
        public ComputerBuilder dateIntro(LocalDate pDate) {
            introduced = pDate;
            return this;
        }

        /**
         * Setter for the ComputerBuilder's discontinued date.
         * @param pDate the Computer's discontinued date
         * @return the ComputerBuilder
         */
        public ComputerBuilder dateDisc(LocalDate pDate) {
            discontinued = pDate;
            return this;
        }

        /**
         * Build a Computer from the ComputerBuilder's attributes.
         * @return a Computer
         */
        public Computer build() {
            if (name.length() < 2) {
                throw new IllegalArgumentException("Computer name must be at least 2 characters");
            }
            Computer computer = new Computer(name);
            computer.id = id;
            computer.company = company;
            computer.discontinued = discontinued;
            computer.introduced = introduced;
            return computer;
        }
    }
}