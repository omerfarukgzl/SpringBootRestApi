package com.Omer.Notes;

public class Notes {
    /*

********************** Entity Oluştruma ****************************


entity package oluşturduk ve User Classı oluşturduk(User Table)
Enitiy Classı için @Entity ve @Id anottaion a ihtiyacımız var.
Table Özelliği ile tablo adını girdik
GeneratedValue İle id nin artım ve başlangıç değerinini belirttik
SequenceGenerator
Coulmn özelliği ile sutun başlığı güncellendi
BU entity Classına lombokdan gelen Data annotation eklenince geter seter to string metodları oluşturuldu
Bunun yerine el ile @ geter @ seter eklenebilir

Ortak Tablo olarak baseclass oluiturulur kim güncelledi ne zaman güncelledi vb;
Base Class ı oluşturan Annotttaion @MappedSupperClass dır
ve Serialzable implement ediyoruz : Bu özellik nesnemiiz networken taşıma veyha diske yazıp okuma özelliği kaznaıdıruoruz;

********************** Entity Oluştruma ****************************





00000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000






******************** Katmanlar Arası Mimari ************************

                   Client
                     ||
                     || İstek
                     \/
                  Controller
                     ||
                     ||    Dto ile veri haberleşme
                     \/
                  Services
                     ||
                     ||   Entity ile haberleşme
                     ||
                     \/
                  Repository ============ > Database
                              interface yardımı






Katmanlı mimaride 3 adet katmanımız var :
*Controller
*Services
*Repository

Controller katmanı : İnternetten gelen sunucuya gelen isteklerin ilk karşılandığı katmandır
Yani dışarıya açtığımız web api katmanıda denebilir

Services Katmanı  : İş kodlarını yazdığımız aldığımız veri kontrolleri validation kontrolleri katmanıdır

Repository Katmanı: Interface yardımıyla database işlşemlerinin yapıldığı katmnadır

Bu Katmanlar arasındaki iletişimi DTO ve entity nesneleri ile gerçekleşir

Controler- Services Dto
Services - Repository Entity


Önce Controller Paketini oluşturduk (api package) controller package de olabilirdi farketmez
Daha sonra controller classı oluşturduk ve
bu classın api olatrak dışarıya açılabilmesi için @Restcontroller annottatiın ekledik
İkinci olarak bu api nin hangi adreste yayınlanacağını belirtmek için @RequestMapping annotation ekledik


Repositoy Katmanını oluşturduk. package name repository
UserRepository Classı oluşturduk. JPA Reposiry extend ettik ve ilk parametre olarak hangi classa hizmet edicekse onu ikinci olarak primary key değeri tipini belirttik (<User,Long>)
Repository için @Repository Annotationunu eklememmize gerek yok çünkü jpa extend edildiği için bunun bir repository katmanı olduğunu anlar
Böylelikle repository interface imize hazır fonksiyonları kullanabilme özelliği kazandırdık db methodları.
Eğer hazır gelen methodlar bu jpa da yoksa örneğin ada göre arama yapılacak method yazacağız o zaman kullanılafak table adını (class adını) daha sonra FindBy edikten sonra firstname alanının seçip method yazabilirz
User FindByFirstname(String firstname){}
eğer burda da bulamazsak @Query  Sorgusuna yazabiliriz.


services katmanını oluşturduk.iş kodları burda oluşturulur.
Katmanlar arası bağlantılar interface üzerinden olduğu için interface oluşturduk.UserServices
Daha Sonra interface leri implemente edicek classlar için servicesImpl paketi açtık. Daha sonra Class oluşturduk.UserServicesImpl
Classı ımızın services olduğunu belirtmek için @Services Annotation unu ekledik.Böylece Proje ayağu kalkerken bu classın instance ı oluşturulacak ve IoC konteynırına eklenecek.


******************** Katmanlar Arası Mimari ************************





00000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000





********************* Katmanlar Arası Bağlantı ************************

***Controller----Services**

Controller Katmanı Services katmanı ile konuşacağı için Controller sınıfına property ekledik.IoC içinde tutulan property ye Inject ettik.


 @Autowired
 private UserServices userServices

  veya constructor ile yapabiliriz. Bu önerilir Çünkü Zorunluluk ortaya koyar oarametre girilmesi gerek constructora ve nullpointer errorundan kurtulururuz.

    private final UserService userService;
    public UserController(UserService userService){
        this.userService=userService;
    }
    veya   private final UserService userService; yanlızca bunu yazarız ve @RequiredArgsContructor Annotationu ile Constructor u otomatik oluşturur.

******************Not*****************

controllerın ustundeki
@RequestMapping(value = "/user")
ve controller içindeki

    @PostMapping(value = "/create")
    public ResponseEntity<User> createUser(@RequestBody User user)
    {
        User resultUser=userService.createUser(user);
        return ResponseEntity.ok(resultUser);
    }

aslında .net core daki controller url si ve action url sdir  @RequestMapping(value = "/user")   ,  @PostMapping(value = "/create")

******************Not*****************


********************* Katmanlar Arası Bağlantı ************************





00000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000





************************ PostMapping Metodu***************************
localhost:8080/user/create  (postman) istek


ResponseEntity sınıfı HttpEntity sınıfına ek olarak builder tasarım desenini kullanarak isteğe yanıt olarak header bilgisi,
HTTP durum kodu gibi bilgileri eklemeyi sağlar.
Geniş kullanım desteği sunan ResponseEntity REST API işlemlerinde sıklıkla kullanılan durum kodları için özel statik metotlara sahiptir.


post seçeneği seçilir
Body Kısmı seçilir ve json formatında bilgiler yazılır.
    {
        "firstname":"Taha",
        "lastname":"Faruk"
    }


Metodumuzu public olarak tanımlıyoruz çünkü dışarıya açaçcağız.
Dönüş Tipini SpringFrameworkden gelen ResponseEntity den dönüyoruz Böylelikle metdolarımıza ortak imza oluşturuyoryuz.
@PostMapping Annotation u ekledik.ve bir path verdik.
İkinci olarak RequestBody Annotation eklendi. Böylece Json formatında gönderilen bilgiyi user nesnesine eşeltirmeyi sağlıyoruz.
    @PostMapping(value = "/create")
    public ResponseEntity<User> createUser(ResponseBody User user)
    {
         User resultUser=userService.createUser(user);
        return ResponseEntity.ok(resultUser);
    }

    **** İstek Clien tdan Controller tarfından karşılandı. Bu istek create isteğiydi
    daha sonra Controller ile services haberleşti ve services deki createUser Fonksiyonuna gitti burtada işlemler yapıldı(kim tarafından oluşturuldu ne zaman oluşturuldu vs.)
    daha sonra bu fonksiyon işlemler sonucundaki user nesnesini Repository. save e gönderdi
    ve veri tabanı işlemleri repositoryde gerçekleşti (save ile)
    ve daha sonra bu işlem bitiminin sonucu resultUser a kaydedildi
    ve ResponseEntity.ok(resultUser) ile oluşturulan kayıt potmanda görülebidli.




     Daha sonra services katmanınnda da aynı method oluşturlur.Interface Uzerinde
     public abstract olarak tanımlamayız ınterface dekileri çünkü default olarak öyledir zten;
     BU INTERFACE İ İMPLEMENT EDEN UserServicesImpl classında override edelim ve içeride kim tarafından tarihi oluşturalım
     ve daha sonra repository.save fonksiyonuna gönderdik.


  @Override
    public User createUser(User user) {
        user.setCreateData(new Date());
        user.setCreatedBy("Admin");
        return userRepository.save(user);
    }

******************Not*****************

services katmanlarında interface olmasının sebebi Controller sınıfınfa services Impl sınıfının Kullanılmasından solayı Dependency Injection uygulanmıştır.

******************Not*****************

************************ PostMapping Metodu****************************




00000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000







********************** GetMapping Metodu*************************

******GetAllUsers*********
localhost:8080/user/getAll  (postman) istek

    @GetMapping(value = "/getAll")
    public ResponseEntity <List<User>> getUser()
    {
        List<User> resultUser=userService.getUsers();
        return ResponseEntity.ok(resultUser);
    }

Öncelikle getAllUsers Metdodunu Controller içerisinde tanımladık.İnternetten gelen ilk isteğe cevap verecek olan controller da tanımlanan bu metoda GetMApping(/getAll) url sini verdik.
getAllUsers Metdounun Generic Tipini List Dönüş Tipini User olarak verdik yani Bize user listesi dönecek.
Daha Sonra List<User> resultUsers değişkenine userServices.getUsers() fonksiyonunu atadık. Controller ile servicesi haberleştirmiş olduk.
userServices.GetUsers() metodunu UserServices Interface inde tanımladık.
Tanımladığımız bu methodu bu interface i implement eden UserServicesImpl classında override ettik
Override edilen bu methodun içinde işlem yapmadık çünkü tüm kullanıcıları koşulsuz getirmek istiyoruz.
Bu Override edilen methodun dönüşüne Repository katmanı  ile haberleşmesi için userRepository.findAll Hazır Jpa fonksiyonunu kullandık.
Repositoryden List User tipinde dönen sonucu json formatında geri dönüşünü sağladık.


    @Override
    public List<User> getUsers() {
        //
        return userRepository.findAll();
    }


******GetAllUsers*********

------------------------------------------

*********getUser**********

localhost:8080/user/getById/100  (postman) istek

    @GetMapping(value = "/getById/{id}")
    public ResponseEntity <User> getUser(@PathVariable ("id") Long id)
    {
        User resultUser=userService.getUser(id);
        return ResponseEntity.ok(resultUser);
    }

Öcelikle getUser methodununu tanımlıyoruz.
GetMapping özelliğğinde url kısmında @GetMapping(/get{id}) veriyoruz.Çünkü bir kullanıcı getirmek istediğimizde o kullanıcının id si ile ona ulaşırız.
Daha sonra     public ResponseEntity <User> getUser(@PathVariable ("id") Long id) tanımlanan method da parametre olarak  @PathVariable ("id") Long id tanımlamaısnı yapıyoruz. Bu tanımlama path den gelen id değikenini Long id parametresi ile eşletirsin anlamına geliyor.
User userResult değişkenine is c.getUser(id); methodundan dönen User nesneisnin atıyoruz. Böylece Controller ile Services Katmanını haberleştiriyoruz.
Daha sonra userServices ınterfacesinde User getUser(Long id) methodunu ve implemet eden UserServicesImpl Classında override ederek bu methodu tanımlıyoruz.
UserServivesImpl Classında ovveride ettiğimiz bu methodun içinde findById methodunu kullandık. Geriye Optional döner bu nullpointerexeption hatasının düşmesini engelliyor.
dönen sonuç Optionalde saklandığı için sonucu isPresent() ile kontrol edebiliyoruz.Yani geriye bir değer döndümü dönmdedi mi . Böylece null pointer hatasını engellemiş olduk.
direk return userRepository.findById(id).get() dediyebilirdik fakat null pointer exeption hatasını engellemek istedik.

    @Override
     public User getUser(Long id) {
            Optional <User> user = userRepository.findById(id);
            if(user.isPresent()) // geriye user döndümü
            {
                return user.get();
            }
            return null;
        }


*******************Not(OPTIONAL)****************

Null olmayan bir değer içerebilen veya içermeyebilen bir kapsayıcı nesnesi. Bir değer varsa, isPresent() true değerini döndürür. Değer yoksa, nesne boş kabul edilir ve isPresent() false döndürür.
orElse() (değer yoksa varsayılan bir değer döndürür) ve ifPresent() (bir değer varsa bir eylem gerçekleştirir) gibi, içerilen bir değerin varlığına veya yokluğuna bağlı olan ek yöntemler sağlanır.
Bu, değere dayalı bir sınıftır; programcılar, eşit olan örnekleri birbirinin yerine geçebilir olarak ele almalı ve örnekleri senkronizasyon için kullanmamalıdır, aksi takdirde öngörülemeyen davranışlar ortaya çıkabilir.
 Örneğin, gelecekteki bir sürümde senkronizasyon başarısız olabilir.
API Notu:
İsteğe bağlı, öncelikle "sonuç yok" ifadesinin açıkça gerekli olduğu ve boş değerin kullanılmasının hatalara neden olabileceği durumlarda bir yöntem dönüş türü olarak kullanılmak üzere tasarlanmıştır.
Türü İsteğe bağlı olan bir değişkenin kendisi hiçbir zaman boş olmamalıdır; her zaman bir İsteğe bağlı örneğe işaret etmelidir

*******************Not(OPTIONAL)*****************

*********getUser**********


********************** GetMapping Metodu*************************






00000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000





************************ PutMapping Metodu**************************
localhost:8080/user/update/100  (postman) istek
and json format update user


    @PutMapping(value = "/update/{id}")
    public ResponseEntity <User> updateUser(@PathVariable ("id") Long id,@RequestBody User user)
    {
        User resultUser=userService.updateUser(id,user);
        return ResponseEntity.ok(resultUser);
    }

 Öncelikle Controller da updateUser methodunu tanımladık.
 Daha sonra bu methodun @PutMapping Kısmına updateUser{id} ekledik
 methodun parametre kısmına path den gelen pathveraible ve guncellenmek istenen user nesnesinden json formatında gelen  @RequestBody User user parametresini ekledik
 User resultUser değişkenine services ınterface inde olan userServices.updateUser(id,user) methodunu atadık.
 Services ınterfaceında tanımlayıp implement eden userServicesImpl classında da override ettik
 Bu override edilen methodda Optional tip dönen userRepository.findById(id) ile guncellenmek istenen kullanıcıyı bulduk ve isPresent() özelliğini kullanarak sonuç geldi mi gelmedimi kontrol edttik
 Eğer sonuç olarak bir user donduyse donen user'a parametreden gelen user nesnesinin özellikleirni set ettik ve save edip geri döndük.

 !!Önce get ile o satırı aldık daha sonra o satıra yeni değer setledik. Setin içine paramtreden gelen bilgiyi get ederek aldık.


    public User updateUser(Long id,User user) {
        Optional <User> finduser = userRepository.findById(id);
        if(finduser.isPresent()) // geriye user döndümü
        {
            finduser.get().setFirstname(user.getFirstname());
            finduser.get().setLastname(user.getLastname());
            finduser.get().setUpdateAt(new Date());
            finduser.get().setUpdateBy("Admin");

            return userRepository.save(finduser.get());
        }
        return null;
    }

************************ PutMapping Metodu**************************





00000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000





************************ DeleteMapping Metodu**************************
localhost:8080/user/delete/100

 @DeleteMapping(value = "delete/{id}")
public ResponseEntity <Boolean> deleteUser(@PathVariable ("id") Long id)
    {
        Boolean resultUser=userService.deleteUser(id);
        return ResponseEntity.ok(resultUser);
    }

Öncelikle Controller da Boolean geri dönüşü olan deleteUser methodunu tanımladık.Methoda parametre olarak silinecek user ın id sinin url üzerinden @PathVeriable üzerinden atadık.
Dönüş tipi boolean olan bir değişkende userServices.deleteUser(id) methodundan gelen sonucu sakladık.
Services ınterfaceınde ve implement eden userServiesImpl Classında tanımlamaları yaptıktan sonra
override edilen method içerisinde  Optional <User> finduser = userRepository.findById(id); işleviyle kullanıcıyı buldurduk.
Eğer kullanıcı bulunduysa userRepository.deleteById(id); işleviyle gönderilen id üzerinden kullanıcıyı sildik.
Sonucunda true geri dönüşümü sağladık.

public Boolean deleteUser(Long id) {
        Optional <User> finduser = userRepository.findById(id);
        if(finduser.isPresent()) // geriye user döndümü
        {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }


************************ DeleteMapping Metodu**************************






00000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000






************************ Dto Dönüşümü ve Model Mapper **************************

Biz Entitylerimizi api aracılığıyla direk dış dunyaya açmak istemeyiz. Guvenlik açığı olabilir.
Bunun yanında entity classlarımızdaki bazı yapılarda clienta gereksiz yere göndermeye gerek yok.
Modele bir alan daha eklendiği zaman tüm clientlarda değişiklik yapmamak için de kulanılır.

!!! Bizim bazı alanlarımız iç modeli etkileyen alanlardır.Örneğin Şuanki dB de olan createdBy createdAt gibi alanlar bizim daha çok iç modelimizi ilgilendiren alanlar.
Yani  bu alanları client da göstermeye gerek yok.Bunun için dto gereklidir !!!


!!! Model mapper ise bu entity ve dto dönüşümünde kullanılır

Uygulamamızda model mapper ın bir instance ını olusturup bunu her istediğmizde kullanmayı sağlayalıum
Her kullanmak istediğimizde new lemeyelim bunun için IoC conteiner ında olışan instance ı kullanalım
Bu configurasyonu yapmak için config paketi içinde ModelMapperConfig classı açıyoruz.
Bu classın config classı olduğunu belli etmek için @Configuration Annotation ekledik.ve Instance Oluşumu için methoda @Bean annotation u verdik.
Daha sonra tam eşleşme sağlayarak oluşturduğumuz modelMapper ı geri döndük.




@Configuration
public class ModelMapperConfig {
    @Bean
    public  ModelMapper getModelMapper()
    {
        ModelMapper modelMapper= new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return modelMapper;

    }
}


Yanlızca fisrtname ve lastname alanlarını göstermek istedik ve dtoya bu alanları ekledik.
Daha sonra @Data Annottaion nu Dto classına atadık.Set Get ToString vb metotların oluşmasını sağladk


!!! Artık Controller ve Services Katmanları Dto İle konuşacak. Services ve Repository Entity katmaı ile konuşacak.

? Tüm Controller Daki User Entity  Class Tipini Artık UserDto ile güncelledik.




---------Post Metodu İçin--------------


   @PostMapping(value = "/create")
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto user)
    {
        UserDto resultUser=userService.createUser(user);
        return ResponseEntity.ok(resultUser);
    }


userServicesImpl Classındaki override edilen post metodu için kullanılan createUser methodunun User kısımlarını UserDto ile güncelledik
Controller - Services Dto
Services - Repository Entity
olduğundan dolayı method içerisinde Dto olarak aldığımız parametreyi entitye dönüştürdük.(Model Mapper ile)

    User user = modelMapper.map(userDto,User.class);
                                        ||
                                        \/
    --- Bu dönüşüm işleminin anlamı (UserDto yu User a çevir )= sonucu User user a ata;

    oluşan user entity nesnesidir.

Çünkü Services Repository ile Entity haberleşmesi yapacaktır.
Model Mapper in kullanılması için ModelMapper Instance oluşturduk.

        private final ModelMapper modelMapper;
        public UserServicesImpl(ModelMapper modelMapper)
        {
            this.modelMapper = modelMapper;
        }

En sonunda return değerinin bir Dto Olması gerekli çünkü Controlller sınıfındali değişkenimiz Dto değişkeni;

    return ModelMapper(userRepository.save(user),UserDto.Class)
    --- Bu dönüşümün anlamı (userRepository ile userı kaydet donen user ı UserDto ya çevir)= sonucu return ile dön


    @Override
    public UserDto createUser(UserDto userDto) {
        User user = modelMapper.map(userDto,User.class);
        user.setCreateData(new Date());
        user.setCreatedBy("Admin");
        return modelMapper.map(userRepository.save(user),UserDto.class);
    }




---------GetAll Metodu İçin--------------


    @GetMapping(value = "/getAll")
        public ResponseEntity <List<UserDto>> getUser()
        {
            List<UserDto> resultUser=userService.getUsers();
            return ResponseEntity.ok(resultUser);
        }
Controller da User kısımlarını UserDto ile güncelledik.Services Interfaccinde ve implement eden class da da güncelledik.
 UserServicesImpl Classında da öncelikle List <User> user = userRepository.findAll(); işleviyle List tipinde user tutttuk.
Daha sonra List<UserDto> userDto= users.stream().map(user -> modelMapper.map(users,UserDto.class)).collect(Collectors.toList());
işleviyle birlikte  stream.map fonskiyonuyla her bir nesneye özgü model mapper dönüşümü yapıyoruz ve işlem sonucunu list olarak dönüyoruz
 return ile controllera userDto listesini Dönüyoruz.

    @Override
        public List<UserDto> getUsers() {

            List<User> users = userRepository.findAll();
            List<UserDto> userDto= users.stream().map(user -> modelMapper.map(user,UserDto.class)).collect(Collectors.toList());
            return userDto ;
        }

---------GetUser Metodu İçin--------------

   @GetMapping(value = "/getById/{id}")
    public ResponseEntity <UserDto> getUser(@PathVariable ("id") Long id)
    {
        UserDto resultUser=userService.getUser(id);
        return ResponseEntity.ok(resultUser);
    }

 UserServicesImpl Classında repository ile services haberleşmesinden sonra sonuç Optional usera atılır.
 gerekli kontroller yapıldıktan sonra return olarak Dto ya çevrilmiş hali dönülür

  @Override
    public UserDto getUser(Long id) {
        Optional <User> user = userRepository.findById(id);
        if(user.isPresent()) // geriye user döndümü
        {
            return modelMapper.map(user.get(),UserDto.class);
        }
        return null;
    }



---------UpdateUser Metodu İçin--------------

    @PutMapping(value = "/update/{id}")
    public ResponseEntity <UserDto> updateUser(@PathVariable ("id") Long id,@RequestBody UserDto userDto)
    {
        UserDto resultUser=userService.updateUser(id,userDto);
        return ResponseEntity.ok(resultUser);
    }

 UserServicesImpl Classında repository ile services haberleşmesinden sonra sonuç Optional usera atılır.
 gerekli kontroller yapıldıktan sonra return olarak Dto ya çevrilmiş hali dönülür

UserServicesImpl Classında Optional <User> tipinde kullanıcıyı bulduk.
daha sonra kontrollerden sonra Dto ile işlemler yaparak return kısmında user nesnesini UserDto ya dönüştürdük ve Controllera gönderdik.



    @Override
        public UserDto updateUser(Long id,UserDto userDto) {
            Optional <User> finduser = userRepository.findById(id);
            if(finduser.isPresent()) // geriye user döndümü
            {
                finduser.get().setFirstname(userDto.getFirstname());
                finduser.get().setLastname(userDto.getLastname());
                finduser.get().setUpdateAt(new Date());
                finduser.get().setUpdateBy("Admin");

                return modelMapper.map(userRepository.save(finduser.get()),UserDto.class);
            }
            return null;
        }



************************ Dto Dönüşümü ve Model Mapper **************************






00000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000







************************ Server Side Pagination **************************





************************ Server Side Pagination **************************















  */

}
