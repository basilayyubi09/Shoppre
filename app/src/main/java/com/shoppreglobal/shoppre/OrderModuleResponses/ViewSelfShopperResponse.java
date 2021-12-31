package com.shoppreglobal.shoppre.OrderModuleResponses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ViewSelfShopperResponse {
    @SerializedName("pkg")
    @Expose
    private Pkg pkg;

    public Pkg getPkg() {
        return pkg;
    }

    public void setPkg(Pkg pkg) {
        this.pkg = pkg;
    }

    public class PackageState {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("state_id")
        @Expose
        private Integer stateId;
        @SerializedName("State")
        @Expose
        private State state;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getStateId() {
            return stateId;
        }

        public void setStateId(Integer stateId) {
            this.stateId = stateId;
        }

        public State getState() {
            return state;
        }

        public void setState(State state) {
            this.state = state;
        }

    }

    public class Pkg {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("invoice")
        @Expose
        private String invoice;
        @SerializedName("PackageState")
        @Expose
        private PackageState packageState;
        @SerializedName("Store")
        @Expose
        private Store store;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getInvoice() {
            return invoice;
        }

        public void setInvoice(String invoice) {
            this.invoice = invoice;
        }

        public PackageState getPackageState() {
            return packageState;
        }

        public void setPackageState(PackageState packageState) {
            this.packageState = packageState;
        }

        public Store getStore() {
            return store;
        }

        public void setStore(Store store) {
            this.store = store;
        }

        public class State {

            @SerializedName("id")
            @Expose
            private Integer id;
            @SerializedName("name")
            @Expose
            private String name;

            public Integer getId() {
                return id;
            }

            public void setId(Integer id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

        }

        public class Store {

            @SerializedName("id")
            @Expose
            private Integer id;
            @SerializedName("name")
            @Expose
            private String name;

            public Integer getId() {
                return id;
            }

            public void setId(Integer id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

        }
    }
}
