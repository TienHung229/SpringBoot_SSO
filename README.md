# 🚀 Ứng dụng Spring Boot tích hợp Single Sign-On (SSO) với Auth0

Dự án này là một **ứng dụng web demo** được xây dựng bằng **Java Spring Boot**, minh họa cách tích hợp cơ chế **đăng nhập một lần (Single Sign-On - SSO)** với nhà cung cấp danh tính (**Identity Provider - IdP**) là **Auth0**.  
Hệ thống sử dụng giao thức **OpenID Connect (OIDC)** để xác thực người dùng và hỗ trợ **Refresh Token** để duy trì phiên đăng nhập.

---

## 📘 Thông tin sinh viên

- Họ và tên: Đinh Tiến Hùng
- Mã số sinh viên: 23020538
- Lớp: 2526I_INT3236E_1

## ✨ Tính năng chính

### 🔐 Tích hợp SSO với Auth0
- Người dùng được chuyển hướng đến trang đăng nhập của **Auth0** để xác thực.  
- Sau khi đăng nhập thành công, **Auth0** trả về thông tin người dùng cho ứng dụng một cách an toàn.

<img width="1341" height="536" alt="image" src="https://github.com/user-attachments/assets/e671014a-03c6-45ad-b588-c808b76e0481" />

<img width="630" height="815" alt="image" src="https://github.com/user-attachments/assets/acb47312-c830-415b-aca0-fb77d9feb390" />


### 🧰 Bảo mật với Spring Security & OIDC
- Sử dụng **Spring Security** và **spring-boot-starter-oauth2-client** để cấu hình luồng OIDC tự động.  
- **ID Token** nhận từ Auth0 chứa thông tin (claims) của người dùng đã được xác thực.

  <img width="923" height="518" alt="image" src="https://github.com/user-attachments/assets/4941e3ca-9fe9-4062-bbbf-4085457ef5f6" />


### 👥 Phân quyền truy cập
| Đường dẫn | Mô tả | Quyền truy cập |
|------------|--------|----------------|
| `/` | Trang chủ của ứng dụng. | Công khai |
| `/profile` | Hiển thị thông tin người dùng sau khi đăng nhập. | Yêu cầu đăng nhập |
| `/logout` | Đăng xuất khỏi ứng dụng và Auth0. | Yêu cầu đăng nhập |

### 🚪 Đăng xuất toàn diện
- Tính năng đăng xuất xóa **phiên làm việc trên ứng dụng** và cả **phiên đăng nhập trên Auth0**, đảm bảo người dùng được đăng xuất hoàn toàn.

### 🔄 Hỗ trợ Refresh Token
- Ứng dụng được cấu hình để **tự động yêu cầu và sử dụng Refresh Token**, giúp gia hạn Access Token mà không cần đăng nhập lại.

---

## 🛠 Công nghệ sử dụng

### Backend
- Java 17  
- Spring Boot 3  
- Spring Web  
- Spring Security  
- Spring Boot OAuth2 Client  

### Template Engine
- Thymeleaf  

### Nhà cung cấp danh tính (IdP)
- Auth0  

### Giao thức
- OpenID Connect (OIDC)

### Build Tool
- Apache Maven  

---

## ⚙️ Hướng dẫn cài đặt và chạy dự án

### 🔧 Yêu cầu hệ thống
- **JDK 17** hoặc cao hơn  
- **Maven 3.6** hoặc cao hơn  
- **IDE** (IntelliJ IDEA, VS Code, Eclipse, ...)

---

### 🧭 Các bước thực hiện

#### 1️⃣ Cấu hình trên Auth0

1. **Tạo Application:**  
   - Đăng nhập vào Auth0, tạo một ứng dụng mới loại **Regular Web Application**.  
2. **Cấu hình URLs:**  

- Allowed Callback URLs: [http://localhost:8080/login/oauth2/code/auth0](http://localhost:8080/login/oauth2/code/auth0)

- Allowed Logout URLs:  [http://localhost:8080](http://localhost:8080)

3. **Lấy thông tin:**  
- Sao chép **Domain**, **Client ID**, và **Client Secret**.
4. **Kích hoạt Refresh Token:**  
- Tạo một API trong Auth0.  
- Vào phần cài đặt và bật tùy chọn **Allow Offline Access**.

---

#### 2️⃣ Cấu hình ứng dụng Spring Boot

1. **Clone dự án** hoặc tạo mới từ các file có sẵn.  
2. Mở tệp `src/main/resources/application.yml` và thêm cấu hình sau:

```yaml
auth0:
  domain: YOUR_AUTH0_DOMAIN

spring:
  security:
    oauth2:
      client:
        registration:
          auth0:
            client-id: YOUR_CLIENT_ID
            client-secret: YOUR_CLIENT_SECRET
````

3. **Chạy dự án**

    * Cách 1: Chạy trực tiếp trong IDE từ file `SsoApplication.java`.
    * Cách 2: Chạy qua terminal:

      ```bash
      ./mvnw spring-boot:run
      ```

4. **Truy cập ứng dụng tại:**
   👉 [http://localhost:8080](http://localhost:8080)


---

## 💡 Tham khảo

* [Auth0 Documentation](https://auth0.com/docs)
* [Spring Security OAuth2 Docs](https://docs.spring.io/spring-security/reference/servlet/oauth2.html)
* [Spring Boot Reference Guide](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/)


