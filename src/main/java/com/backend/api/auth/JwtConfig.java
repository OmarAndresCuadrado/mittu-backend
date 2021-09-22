package com.backend.api.auth;

public class JwtConfig {
	
	public static final String RSA_PRIVADA = "-----BEGIN RSA PRIVATE KEY-----\r\n"
			+ "MIIEpAIBAAKCAQEAtR/01a32wGGvZkxQwhG8UEQxU2GiaOEJRvIWLj775RQl2nC0\r\n"
			+ "KywujgVi5wEHO1JA223iwXF/9k0hbUUXTgbSMqDMTEhgb5ZZIGtIsXrYON/0kUVr\r\n"
			+ "RD3Ew1v0oGNoI1X+85qt8Wd/YlLDU+BJuZCsDSnauocftRdT9VJjLW33BYU2sFd3\r\n"
			+ "54ByD64wjOFGGHzmFLka5Xzd/dUr+Oc9hf3tGAG8L79wJXlamjtx7r2eQW9lMRqZ\r\n"
			+ "+w3ia75HVOKYAvDI8PoHs9TBybllUh63rSL8wt4bLBC8TKxgWsOt4gF5/RCo0noX\r\n"
			+ "SFjaukYo8TYpjyjVy35rmhdyOUACF++VAWzRFQIDAQABAoIBAERUbfU+z9v7/kPF\r\n"
			+ "fqH4NubBEyq1pqOSYgGVyrBfqn/dffpOkmnHHTkO5yTmymeivD0L04PO4ct1harH\r\n"
			+ "aedRw3K+HtSYaWkveygz0RTDl1a9hnJXkgQImXZp9nubhOnvw2L7c6uis/fP6Uh3\r\n"
			+ "UNjkr0O4CoBcAaY7yry9/BRja9F0sRC6gtZ0lRaj1xA0jQWfXNvrrkZ1ojvDtkV4\r\n"
			+ "tVges/osvsZ8urhUm7drdrD8iQRsn1CZfJpJOjyBdmnf/kyUZJTVQe+bWSD0qLn3\r\n"
			+ "JX8S6yGfY1Uf3CxLmAUEo6MmfYbzYLoeVyBvxYs+aO88fHa50ZDFkMNdc3CUbLfX\r\n"
			+ "EpGLPYECgYEA6W8J1aBMFJHZXyWMN8DBvdrBU2EwYkZcuuVH3oR2c35zjBdqkH4A\r\n"
			+ "1h30zQU6M/YLtejofVzrqcFVkPhQczFkdhbzk2UybQlAINRwtuV5Cp+qEmWj5V3x\r\n"
			+ "RqUUoHcYUkxFEn4wZi1UbMQz5vtdHAVC8O3sPFvUfE/gPfjJ0eRH6o0CgYEAxqJj\r\n"
			+ "wVTBBdxV+xGk2rbczby3b4Y/DpFYsex+ak3m8NS7shH/9K7kM64cAQT7Ibi2xThy\r\n"
			+ "6tYm+prMP8p3oo4tArJUFT7h24U6gMRr8rdsiuuNxosqWfmgQkA3uPhUIPdRyE3r\r\n"
			+ "VJeSA7x13UIKd3381g1/8e+g8t38WezT5ZENYqkCgYAObdmqxczOHLHWCskfDSAs\r\n"
			+ "rx/yGnjWKklDGDcYDxIPU4qq5yukINXGZnWiUqdlXNkednYggaTjilrSqja22B/A\r\n"
			+ "mOyXeNLkIIVBQkBxXb/coz/tATFfVS470qpyvMnZFHPUj85IEpc8XehjZ7g6qiyY\r\n"
			+ "HezTUk3RHVLcIcu/2J+FZQKBgQCvOhYyOOhGmUcoQzpgzyweOrq/oXgDC+lonC4w\r\n"
			+ "7SQrlB84SXS4j4g0pwWBSLmZ1+80MEYAMIcWUHSaWawMNMw0MsjGLUDlfLRNX/my\r\n"
			+ "lmkkbAQYADCOSw47I5eEj1z37/WvdD2D1Go07Y40b8v3+aaP1jzmWvRrC7VQRwHu\r\n"
			+ "5HUgcQKBgQCaOMQggs2eiaFYjfgDs00kNGV8ZXZbFkrRrs85x7M0MSLPj/UxGtPQ\r\n"
			+ "Jm4Tb//UGuLT5ZmOllLNuZlZQhZLFkD/YANXkRB/Io/kHRS3mGodq1zelr18ooPu\r\n"
			+ "0j50TpcSefHZ+o8h1xHv4Prbq/wOVeARSxKo+Dy74SjNZnxq9AQ8ig==\r\n"
			+ "-----END RSA PRIVATE KEY-----";
	
	public static final String RSA_PUBLICA = "-----BEGIN PUBLIC KEY-----\r\n"
			+ "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAtR/01a32wGGvZkxQwhG8\r\n"
			+ "UEQxU2GiaOEJRvIWLj775RQl2nC0KywujgVi5wEHO1JA223iwXF/9k0hbUUXTgbS\r\n"
			+ "MqDMTEhgb5ZZIGtIsXrYON/0kUVrRD3Ew1v0oGNoI1X+85qt8Wd/YlLDU+BJuZCs\r\n"
			+ "DSnauocftRdT9VJjLW33BYU2sFd354ByD64wjOFGGHzmFLka5Xzd/dUr+Oc9hf3t\r\n"
			+ "GAG8L79wJXlamjtx7r2eQW9lMRqZ+w3ia75HVOKYAvDI8PoHs9TBybllUh63rSL8\r\n"
			+ "wt4bLBC8TKxgWsOt4gF5/RCo0noXSFjaukYo8TYpjyjVy35rmhdyOUACF++VAWzR\r\n"
			+ "FQIDAQAB\r\n"
			+ "-----END PUBLIC KEY-----";

}
