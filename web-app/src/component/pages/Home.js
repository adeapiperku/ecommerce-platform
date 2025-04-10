import React, { useEffect, useState } from "react";
import { useLocation } from "react-router-dom";
import ProductList from "../common/ProductList";
import Pagination from "../common/Pagination";
import ApiService from "../../service/ApiService";
import "../../style/home.css";

const Home = () => {
    const location = useLocation();
    const [allProducts, setAllProducts] = useState([]);
    const [products, setProducts] = useState([]);
    const [currentPage, setCurrentPage] = useState(1);
    const [totalPages, setTotalPages] = useState(0);
    const [error, setError] = useState(null);
    const itemsPerPage = 9;

    useEffect(() => {
        const fetchProducts = async () => {
            try {
                let fetchedProducts = [];
                const queryParams = new URLSearchParams(location.search);
                const searchItem = queryParams.get("search");

                if (searchItem) {
                    const response = await ApiService.searchProducts(searchItem);
                    fetchedProducts = response.productList || [];
                } else {
                    const response = await ApiService.getAllProducts();
                    fetchedProducts = response.productList || [];
                }

                setAllProducts(fetchedProducts);
                setTotalPages(Math.ceil(fetchedProducts.length / itemsPerPage));
                setCurrentPage(1);
            } catch (error) {
                setError(error.response?.data?.message || error.message || "Unable to fetch products");
            }
        };

        fetchProducts();
    }, [location.search]);

    useEffect(() => {
        const start = (currentPage - 1) * itemsPerPage;
        const end = start + itemsPerPage;
        setProducts(allProducts.slice(start, end));
    }, [currentPage, allProducts]);

    return (
        <div className="home">
            {error ? (
                <p className="error-message">{error}</p>
            ) : (
                <div>
                    <ProductList products={products} />
                    {totalPages > 1 && (
                        <Pagination
                            currentPage={currentPage}
                            totalPages={totalPages}
                            onPageChange={(page) => setCurrentPage(page)}
                        />
                    )}
                </div>
            )}
        </div>
    );
};

export default Home;
