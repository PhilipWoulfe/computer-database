package com.excilys.formation.dto;

import com.excilys.formation.validation.DateAnterior;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import java.io.Serializable;
import java.util.Objects;

/**
 * DTO class for computers.
 *
 * @author kfuster
 */
@DateAnterior
public class ComputerDto implements Serializable {
    private static final long serialVersionUID = -8940655744113113836L;
    private Long id;
    @NotNull
    @Size(min = 2, max = 40)
    private String name;
    private String introduced;
    private String discontinued;
    @Min(0)
    private Long companyId;
    private String companyName;

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

    public String getIntroduced() {
        return introduced;
    }

    public void setIntroduced(String introduced) {
        this.introduced = introduced;
    }

    public String getDiscontinued() {
        return discontinued;
    }

    public void setDiscontinued(String discontinued) {
        this.discontinued = discontinued;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((companyId == null) ? 0 : companyId.hashCode());
        result = prime * result + ((companyName == null) ? 0 : companyName.hashCode());
        result = prime * result + ((discontinued == null) ? 0 : discontinued.hashCode());
        result = (int) (prime * result + ((id == null) ? 0 : id));
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
        ComputerDto other = (ComputerDto) obj;
        if (!Objects.equals(companyId, other.companyId)) {
            return false;
        }
        if (companyName == null) {
            if (other.companyName != null) {
                return false;
            }
        } else if (!companyName.equals(other.companyName)) {
            return false;
        }
        if (discontinued == null) {
            if (other.discontinued != null) {
                return false;
            }
        } else if (!discontinued.equals(other.discontinued)) {
            return false;
        }
        if (!Objects.equals(id, other.id)) {
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
        StringBuilder stringBuilder = new StringBuilder().append("Computer : [id=").append(id).append(", name=")
                .append(name);
        if (introduced != null) {
            stringBuilder.append(", introduced=").append(introduced);
        }
        if (discontinued != null) {
            stringBuilder.append(", discontinued=").append(discontinued);
        }
        if (companyId != null && companyName != null) {
            stringBuilder.append(", Company : [companyId=").append(companyId).append(", companyName=")
                    .append(companyName).append("]]");
        }
        return stringBuilder.toString();
    }

    public static class ComputerDtoBuilder {
        private Long id;
        private String name;
        private String introduced;
        private String discontinued;
        private Long companyId;
        private String companyName;

        /**
         * ComputerDtoBuilder's constructor.
         *
         * @param pName the computer's name
         */
        public ComputerDtoBuilder(String pName) {
            name = pName;
        }

        /**
         * Setter for the ComputerDtoBuilder's companyId.
         *
         * @param pCompanyId the Company's Id
         * @return the ComputerDtoBuilder
         */
        public ComputerDtoBuilder companyId(Long pCompanyId) {
            companyId = pCompanyId;
            return this;
        }

        /**
         * Setter for the ComputerDtoBuilder's company.
         *
         * @param pCompanyName the Computer's Company
         * @return the ComputerDtoBuilder
         */
        public ComputerDtoBuilder companyName(String pCompanyName) {
            companyName = pCompanyName;
            return this;
        }

        /**
         * Setter for the ComputerDtoBuilder's id.
         *
         * @param pId the Computer's Id
         * @return the ComputerDtoBuilder
         */
        public ComputerDtoBuilder id(Long pId) {
            id = pId;
            return this;
        }

        /**
         * Setter for the ComputerDtoBuilder's introduced date.
         *
         * @param pDate the Computer's introduced date
         * @return the ComputerDtoBuilder
         */
        public ComputerDtoBuilder introduced(String pDate) {
            introduced = pDate;
            return this;
        }

        /**
         * Setter for the ComputerDtoBuilder's discontinued date.
         *
         * @param pDate the Computer's discontinued date
         * @return the ComputerDtoBuilder
         */
        public ComputerDtoBuilder discontinued(String pDate) {
            discontinued = pDate;
            return this;
        }

        /**
         * Build a ComputerDto from the ComputerDtoBuilder's attributes.
         *
         * @return a ComputerDto
         */
        public ComputerDto build() {
            if (name.length() < 2) {
                throw new IllegalArgumentException("Computer name must be at least 2 characters");
            }
            ComputerDto computerDto = new ComputerDto();
            computerDto.id = id;
            computerDto.name = name;
            computerDto.companyId = companyId;
            computerDto.companyName = companyName;
            computerDto.discontinued = discontinued;
            computerDto.introduced = introduced;
            return computerDto;
        }
    }
}