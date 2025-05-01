import React, { useEffect } from 'react';
import { Navigate } from 'react-router-dom';
import { useAuth } from '../../contexts/AuthContext';

const LogoutPage = () => {
    const { logout } = useAuth();

    useEffect(() => {
        const performLogout = async () => {
            await logout();
        };

        performLogout();
    }, [logout]);

    return <Navigate to="/" replace />;
};

export default LogoutPage;