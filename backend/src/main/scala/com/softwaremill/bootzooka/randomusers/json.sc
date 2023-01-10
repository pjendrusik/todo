
import cats.syntax.either._
import io.circe._, io.circe.parser._

val jsonRaw =
"""{"results":[{"gender":"male","name":{"title":"Mr","first":"اميرعلي","last":"کریمی"},"location":{"street":{"number":5698,"name":"شهید همت"},"city":"تهران","state":"گیلان","country":"Iran","postcode":98429,"coordinates":{"latitude":"14.1236","longitude":"-91.3722"},"timezone":{"offset":"-10:00","description":"Hawaii"}},"email":"myraaly.khrymy@example.com","login":{"uuid":"3d1985ff-d5ed-4ba3-a944-fb0bf9ca3103","username":"crazyfish955","password":"marsha","salt":"2w5ZTOn5","md5":"2f9bb2f7700af33acf0ba10fe8b5a70d","sha1":"e1b103ae02cc7b09f8efa0cb02876a5a315a5e90","sha256":"db6bd780fe2522bcbe87b471c05ee8601353d4d523ba59e7292b78e24810aa86"},"dob":{"date":"1986-02-23T08:38:27.852Z","age":36},"registered":{"date":"2003-04-29T23:29:03.473Z","age":19},"phone":"095-76210250","cell":"0900-153-7058","id":{"name":"","value":null},"picture":{"large":"https://randomuser.me/api/portraits/men/73.jpg","medium":"https://randomuser.me/api/portraits/med/men/73.jpg","thumbnail":"https://randomuser.me/api/portraits/thumb/men/73.jpg"},"nat":"IR"},{"gender":"female","name":{"title":"Mrs","first":"Isabelle","last":"White"},"location":{"street":{"number":4586,"name":"Puhinui Road"},"city":"Christchurch","state":"Marlborough","country":"New Zealand","postcode":56594,"coordinates":{"latitude":"8.3258","longitude":"-173.5238"},"timezone":{"offset":"0:00","description":"Western Europe Time, London, Lisbon, Casablanca"}},"email":"isabelle.white@example.com","login":{"uuid":"10285b18-cce7-4d67-bb16-840e5ccc9fd0","username":"ticklishbear500","password":"xxxxxx","salt":"pa6tWVUK","md5":"b4f624a73211f9b2fb312917a26afe20","sha1":"a122752dd3526e3775991f9c97e3c09343cd2ca4","sha256":"bf2c763d1de6b88a4d58fdbdc7caa370376aa186e9c1567dbe68d29c32a4695f"},"dob":{"date":"1993-12-30T17:01:59.427Z","age":29},"registered":{"date":"2007-10-02T16:35:33.442Z","age":15},"phone":"(235)-976-7936","cell":"(156)-037-0280","id":{"name":"","value":null},"picture":{"large":"https://randomuser.me/api/portraits/women/6.jpg","medium":"https://randomuser.me/api/portraits/med/women/6.jpg","thumbnail":"https://randomuser.me/api/portraits/thumb/women/6.jpg"},"nat":"NZ"},{"gender":"female","name":{"title":"Ms","first":"Madhura","last":"Salian"},"location":{"street":{"number":4436,"name":"Bhootnath Rd"},"city":"Belgaum","state":"Dadra and Nagar Haveli","country":"India","postcode":96045,"coordinates":{"latitude":"-9.9024","longitude":"133.0039"},"timezone":{"offset":"-3:00","description":"Brazil, Buenos Aires, Georgetown"}},"email":"madhura.salian@example.com","login":{"uuid":"41359fba-ce5a-4199-95b1-3d1885ce5711","username":"tinyrabbit401","password":"bear1","salt":"MuzAmRCG","md5":"9a78a5f747d80f55465b2cc2d10ebd54","sha1":"648dc69202b499114fddfabc6ad5aabeee298fcb","sha256":"b9b323e85240cedda6f79bc925c50431c43c7c24033ef0848b081a91efba8a05"},"dob":{"date":"1963-03-03T07:58:46.408Z","age":59},"registered":{"date":"2014-06-08T18:33:44.371Z","age":8},"phone":"7816369780","cell":"8723595578","id":{"name":"UIDAI","value":"869388062959"},"picture":{"large":"https://randomuser.me/api/portraits/women/72.jpg","medium":"https://randomuser.me/api/portraits/med/women/72.jpg","thumbnail":"https://randomuser.me/api/portraits/thumb/women/72.jpg"},"nat":"IN"},{"gender":"female","name":{"title":"Miss","first":"Esma","last":"Öztürk"},"location":{"street":{"number":5751,"name":"Maçka Cd"},"city":"Kilis","state":"Konya","country":"Turkey","postcode":76656,"coordinates":{"latitude":"-49.2189","longitude":"164.1817"},"timezone":{"offset":"+6:00","description":"Almaty, Dhaka, Colombo"}},"email":"esma.ozturk@example.com","login":{"uuid":"a5663b78-1ec5-459b-8d19-59fcd09c458e","username":"beautifulmouse960","password":"dogpound","salt":"zhC4qOTd","md5":"1dbb00a0d7510cf9f50b2bd4b4380c29","sha1":"32995ed051a1e8dd888606ac062f4cd1a11bab62","sha256":"69887e5e1e96c736c28600ddd585fd4d1330d7433919217386f8264f21f6529d"},"dob":{"date":"1946-03-18T05:06:48.740Z","age":76},"registered":{"date":"2008-01-17T04:55:55.931Z","age":14},"phone":"(233)-359-4328","cell":"(437)-966-9706","id":{"name":"","value":null},"picture":{"large":"https://randomuser.me/api/portraits/women/36.jpg","medium":"https://randomuser.me/api/portraits/med/women/36.jpg","thumbnail":"https://randomuser.me/api/portraits/thumb/women/36.jpg"},"nat":"TR"},{"gender":"female","name":{"title":"Miss","first":"Hebe","last":"da Cruz"},"location":{"street":{"number":3619,"name":"Rua Carlos Gomes"},"city":"Teresópolis","state":"Goiás","country":"Brazil","postcode":14486,"coordinates":{"latitude":"10.0542","longitude":"22.9989"},"timezone":{"offset":"+3:30","description":"Tehran"}},"email":"hebe.dacruz@example.com","login":{"uuid":"bddaf6de-094f-48ed-b5f3-c9bf8b627e4a","username":"purplepanda683","password":"roadking","salt":"dhuyGuR0","md5":"54e1071e8b5e60168a4c568397b07a6f","sha1":"37353c85c7140b8dc11e79015efee2a2684c8edf","sha256":"f9e5eb288fbe654901061b1c48744ae2a9935012e8d9c4bf46ead1d7c491167f"},"dob":{"date":"1991-10-11T01:51:44.194Z","age":31},"registered":{"date":"2016-09-12T09:26:56.168Z","age":6},"phone":"(52) 4581-9958","cell":"(78) 5568-3505","id":{"name":"CPF","value":"850.523.426-96"},"picture":{"large":"https://randomuser.me/api/portraits/women/23.jpg","medium":"https://randomuser.me/api/portraits/med/women/23.jpg","thumbnail":"https://randomuser.me/api/portraits/thumb/women/23.jpg"},"nat":"BR"}],"info":{"seed":"731f95c7e4fa7ac3","results":5,"page":1,"version":"1.4"}}"""

case class Person(firstName: String, lastName: String, email: String, password: String, birthDate: String)
case class Result(persons: List[Person])

implicit val decodePerson: Decoder[Person] = new Decoder[Person] {
  final def apply(c: HCursor): Decoder.Result[Person] = {
    val name = c.downField("name")
    val login = c.downField("login")
    val dob = c.downField("dob")

    for {
      firstName <- name.downField("first").as[String]
      lastName <- name.downField("last").as[String]
      email <- c.downField("email").as[String]
      password <- login.downField("password").as[String]
      birthDate <- dob.downField("date").as[String]
    } yield Person(firstName, lastName, email, password, birthDate)
  }
}

implicit val decodeResult: Decoder[Result] = new Decoder[Result] {
  override def apply(c: HCursor): Decoder.Result[Result] = {
    for {
      persons <- c.downField("results").as[List[Person]]
    } yield Result(persons)
  }
}

decode[Result](jsonRaw) match {
  case Left(value) => ???
  case Right(result) => println(result.persons.map(_.lastName))
}
