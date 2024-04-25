# DoogyParadise寵物平台網站

這是一個關於寵物的多系統平台，包含會員、商城、活動、旅館、動態牆。
會員系統:登入、註冊、忘記密碼、Google OAuth2第三方登入。

## 這是網站的後端程式
使用Spring Boot進行開發，所有的依賴都在pom檔，properties設定請看以下的快速開始。

## 環境
- MySQL:8.0.31
- JDK 17
## 功能特色

- 功能1：串接第3方
    - Google OAuth2第三方登入
    - Line Pay、綠界支付
    - Google Gemini API
     
- 功能2：開發風格
    - RESTful
    - 前後端分離
    - 後端三層式架構:dao、service、controller

## 快速開始

- 使用Spring Boot在Maven專案運行localhost 8080，Vue專案使用vite套件運行在localhost 5173。

- SQL Query位於DoogyParadise_SQL.sql，可直接於MySQL建置資料庫環境，員工須於資料庫自行新增一筆。

- application.properties與Google第三方登入的properties設定在config_file檔，需自行設定(並放入專案resource資料夾中)。

- Google OAuth2第三方登入需要申請Google Cloud 第三方登入申請憑證(如果不想使用可以註解掉)。

- Google Gemini API:需自行去Google AI Studio申請API Key，截至2024/4月底前都是免費。

- 網站內mail發信功能必須至google帳號開通二階段驗證與應用程式密碼。

## 進階
- 進階1
    - 結合個人github上的[Python Flask專案](https://github.com/ruankairex/DoogyParadise_back_Flask)與MongoDB(相關設定會至於Python Flask專案)，可以開通員工後台中推文圖表的功能
    - 一旦 Flask 專案部署完成並且 MongoDB 連接設定完成，就可以開始在員工後台中使用推文圖表的功能。這些圖表可以使用前端 JavaScript 圖表庫（Chart.js）來呈現從 MongoDB 中獲取的推文數據，以便用戶可以直觀地了解推文的情況。

- 進階2
    - 在個人github會發布另一個cloud版本(未上傳)，此版本多了Nginx、簡單Azure VM佈署、域名與SSL取得簡單教學，佈署完成後即可使用https://...來呼叫前端。
    - 為了結合Nginx的雙機熱備，使用Redis為儲存HttpSession的使用者資訊，達成主機斷線、備機頂替時使用者資訊不會遺失。

## 維護
此專案目前仍在維護與更新，會把我之後所學慢慢加上去。
- 2024/04/24 新增:RoomService，訂房時的執行序鎖，防止兩筆訂單互搶(但還沒找到測試方法)
- 2024/04/25 新增:此專案的第一個單元測試(unit test)。
### 持續更新中...
