# CryptWalletAppDemo
Приложение криптобиржа. В системе предполагается две роли: пользователь и администратор.

Данные о пользователях и администраторах, пользовательских кошельках, текущий курс валют и логи транзакций содержаться в БД postgres (дополнительное задание).

Подключен spring security, поэтому после авторизации в запросах не требуется вводить секретный ключ, который выполняет роль пароля при авторизации(дополнительное задание).


Сделана документация методов с помощью фреймворка swagger (дополнительное задание).

Можно изменить responce с помощью заголовка accept: accept:application/json или accept:application/xml (дополнительно задание).

АВТОРИЗАЦИЯ URL:"/login" POST метод 
curl -X 'POST' \
  'http://localhost:8080/login' \
  -H 'accept: */*' \
  -H 'Content-Type: application/x-www-form-urlencoded' \
  -d 'username=admin1&password=13740a61-afb5-327a-8d20-226a97eaf94e'

Пользовательские REST-запросы.
1. URL:"/registration" POST метод - регистрация. Регистрирует пользователя, который передает в тело уникальные логин и почту:
{
  "username": "example",
  "email": "example@mail.com"
}
В ответ зарегистрированный пользователь получает секретный ключ (пароль) вида xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx:
{
  "secretKey": "xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx"
}
2. URL:"/getratios" GET запрос - Просмотр актуальных курсов валют (данный запрос будет доступен и пользователю и администратору).
Пользователь может запросить актуальные курсы валют. Для этого он в запросе должен указать тип валюты, относительно которой он хочет узнать курс. 
Пример: /getratios?wallet=rub
Пользователь для валюты TON получит в ответ, что 1 rub можно приобрести за 0,002 ton или 1.0E-7 btc.
{
  "btc": "1.0E-7",
  "ton": "0.002"
}
3. URL:"/user/getwallets" GET запрос - Просмотр баланса своего кошелька.
Пользователь может запросить баланс своего кошелька (при авторизации был уже введен секретный ключ, повторно вводить его не надо).
В ответ пользователь получает баланс всех своих кошельков:
{
  "btc": "0.1",
  "ton": "10.0",
  "rub": "1000000.0"
}
4. URL:"/user/deposit" POST запрос - Пополнение своего кошелька извне.
Пользователь может пополнить кошёлок. Для этого достаточно просто отправить запрос с типом валюты и размер транзакции:
{
  "walletTo": "btc",
  "amountTo": 0.001
}
В ответ получим обновленный баланс кошелька:
{
  "walletTo": "btc",
  "amountTo": 0.10400000000000001
}
5. URL:"/user/withdraw" POST запрос - Вывод валюты с кошелька на внешний кошелек.
Требуется указать тип валюты, размер транзакции и внешний кошелек. У пользователя должно быть достаточно средств на кошельке.
{
  "walletFrom": "rub",
  "walletTo": "ASDK2131zczKSAK",
  "amountFrom": 1234
}
В ответ получим обновленный баланс кошелька:
{
  "walletFrom": "rub",
  "amountFrom": 998766
}
6. URL:"/user/convert" POST запрос - Конвертация одной валюты в другую.
Требуется указать тип валют для конвертации и количесто конвертируемой валюты. У пользователя должно быть достаточно средств на кошельке.
{
  "walletFrom": "rub",
  "walletTo": "btc",
  "amountFrom": 1000
}
В ответе сообщается сколько денег списано и сколько зачислено.
{
  "walletFrom": "rub",
  "walletTo": "btc",
  "amountFrom": 1000,
  "amountTo": 0.45
}


REST-запросы администратора.
1. URL:"/admin/getsum" GET запрос - Получить общее количество конкретной валюты у всех пользователей.
Требуется указать тип валюты, например необходимо узнать сколько всего на бирже rub:
/admin/ggetsum?wallet=rub
В ответ получим тип валюты и ее количество:
{
  "rub": "1003766.0"
}
2. URL:"/admin/transactioncount" GET запрос - Получить количество произведенных транзакций на бирже за определенное время.
Необходимо указать промежуточную дату: 
/admin/transactioncount?dateFrom=2023-03-01&dateTo=2023-03-01
В ответ получим количество транзакций:
{
  "transaction_count": "16"
}
3. URL:"/admin/setratio POST запрос - Устанавливает курс валюты относительно других указанных валют.
Необходимо задать тип базовой валюты и цену на нее в других валютах:
{
  "baseCurrency": "ton",
  "walletRates": {
    "rub": 1224,
    "btc": 0.002
  }
}
В ответе будут указана цена базовой валюты относительно указанных:
{
  "walletRates": {
    "rub": 1224,
    "btc": 0.002
  }
}

