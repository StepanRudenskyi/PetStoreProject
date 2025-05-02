import { useNavigate, useSearchParams } from "react-router-dom";
import { useAuth } from "../../contexts/AuthContext";
import { useEffect } from "react";

const OAuth2RedirectPage = () => {
  const [searchParams] = useSearchParams();
  const navigate = useNavigate();
  const { loginWithToken } = useAuth();

  useEffect(() => {
    const token = searchParams.get("token");

    if (token) {
      loginWithToken(token)
        .then(() => {
          navigate("/");
        })
        .catch((error) => {
          console.error("Error during OAuth2 redirect: ", error);
          navigate("/login?error=oauth2_token_invalid");
        });
    } else {
      navigate("login?error=oauth2_no_token");
    }
  }, [searchParams, navigate, loginWithToken]);

  return (
    <div>
      <p>Processing login with Google...</p>
    </div>
  );
};

export default OAuth2RedirectPage;
