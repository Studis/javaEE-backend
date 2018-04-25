package si.fri.tpo.team7.api.servlet.seeding;

import si.fri.tpo.team7.beans.users.MunicipalitiesBean;
import si.fri.tpo.team7.entities.users.Municipality;

public class MunicipalitiesSeeder extends Seeder {
    private MunicipalitiesBean municipalitiesBean;

    public MunicipalitiesSeeder(MunicipalitiesBean municipalitiesBean) {
        super("municipalities");
        this.municipalitiesBean = municipalitiesBean;
    }

    private void Add(int id, String name){
        Municipality m = new Municipality();
        m.setId(id);
        m.setName(name);
        municipalitiesBean.add(m);
    }

    @Override
    protected void SeedContent() {

        Add(213,	"Ankaran");
        Add(1,	"Ajdovščina");
        Add(195,	"Apače");
        Add(2,	"Beltinci");
        Add(148,	"Benedikt");
        Add(149,	"Bistrica ob Sotli");
        Add(3,	"Bled");
        Add(150,	"Bloke");
        Add(4,	"Bohinj");
        Add(5,	"Borovnica");
        Add(6,	"Bovec");
        Add(151,	"Braslovče");
        Add(7,	"Brda");
        Add(8,	"Brezovica");
        Add(9,	"Brežice");
        Add(152,	"Cankova");
        Add(11,	"Celje");
        Add(12,	"Cerklje na Gorenjskem");
        Add(13,	"Cerknica");
        Add(14,	"Cerkno");
        Add(153,	"Cerkvenjak");
        Add(196,	"Cirkulane");
        Add(15,	"Črenšovci");
        Add(16,	"Črna na Koroškem");
        Add(17,	"Črnomelj");
        Add(18,	"Destrnik");
        Add(19,	"Divača");
        Add(154,	"Dobje");
        Add(20,	"Dobrepolje");
        Add(155,	"Dobrna");
        Add(21,	"Dobrova - Polhov Gradec");
        Add(156,	"Dobrovnik");
        Add(22,	"Dol pri Ljubljani");
        Add(157,	"Dolenjske Toplice");
        Add(23,	"Domžale");
        Add(24,	"Dornava");
        Add(25,	"Dravograd");
        Add(26,	"Duplek");
        Add(27,	"Gorenja vas - Poljane");
        Add(28,	"Gorišnica");
        Add(207,	"Gorje");
        Add(29,	"Gornja Radgona");
        Add(30,	"Gornji Grad");
        Add(31,	"Gornji Petrovci");
        Add(158,	"Grad");
        Add(32,	"Grosuplje");
        Add(159,	"Hajdina");
        Add(160,	"Hoče - Slivnica");
        Add(161,	"Hodoš");
        Add(162,	"Horjul");
        Add(34,	"Hrastnik");
        Add(35,	"Hrpelje - Kozina");
        Add(36,	"Idrija");
        Add(37,	"Ig");
        Add(38,	"Ilirska Bistrica");
        Add(39,	"Ivančna Gorica");
        Add(40,	"Izola");
        Add(41,	"Jesenice");
        Add(163,	"Jezersko");
        Add(42,	"Juršinci");
        Add(43,	"Kamnik");
        Add(44,	"Kanal");
        Add(45,	"Kidričevo");
        Add(46,	"Kobarid");
        Add(47,	"Kobilje");
        Add(48,	"Kočevje");
        Add(49,	"Komen");
        Add(164,	"Komenda");
        Add(50,	"Koper");
        Add(197,	"Kostanjevica na Krki");
        Add(165,	"Kostel");
        Add(51,	"Kozje");
        Add(52,	"Kranj");
        Add(53,	"Kranjska Gora");
        Add(166,	"Križevci");
        Add(54,	"Krško");
        Add(55,	"Kungota");
        Add(56,	"Kuzma");
        Add(57,	"Laško");
        Add(58,	"Lenart");
        Add(59,	"Lendava");
        Add(60,	"Litija");
        Add(61,	"Ljubljana");
        Add(62,	"Ljubno");
        Add(63,	"Ljutomer");
        Add(208,	"Log - Dragomer");
        Add(64,	"Logatec");
        Add(65,	"Loška dolina");
        Add(66,	"Loški Potok");
        Add(167,	"Lovrenc na Pohorju");
        Add(67,	"Luče");
        Add(68,	"Lukovica");
        Add(69,	"Majšperk");
        Add(198,	"Makole");
        Add(70,	"Maribor");
        Add(168,	"Markovci");
        Add(71,	"Medvode");
        Add(72,	"Mengeš");
        Add(73,	"Metlika");
        Add(74,	"Mežica");
        Add(169,	"Miklavž na Dravskem polju");
        Add(75,	"Miren - Kostanjevica");
        Add(212,	"Mirna");
        Add(170,	"Mirna Peč");
        Add(76,	"Mislinja");
        Add(199,	"Mokronog - Trebelno");
        Add(77,	"Moravče");
        Add(78,	"Moravske Toplice");
        Add(79,	"Mozirje");
        Add(80,	"Murska Sobota");
        Add(81,	"Muta");
        Add(82,	"Naklo");
        Add(83,	"Nazarje");
        Add(84,	"Nova Gorica");
        Add(85,	"Novo mesto");
        Add(86,	"Odranci");
        Add(171,	"Oplotnica");
        Add(87,	"Ormož");
        Add(88,	"Osilnica");
        Add(89,	"Pesnica");
        Add(90,	"Piran");
        Add(91,	"Pivka");
        Add(92,	"Podčetrtek");
        Add(172,	"Podlehnik");
        Add(93,	"Podvelka");
        Add(200,	"Poljčane");
        Add(173,	"Polzela");
        Add(94,	"Postojna");
        Add(174,	"Prebold");
        Add(95,	"Preddvor");
        Add(175,	"Prevalje");
        Add(96,	"Ptuj");
        Add(97,	"Puconci");
        Add(98,	"Rače - Fram");
        Add(99,	"Radeče");
        Add(100,	"Radenci");
        Add(101,	"Radlje ob Dravi");
        Add(102,	"Radovljica");
        Add(103,	"Ravne na Koroškem");
        Add(176,	"Razkrižje");
        Add(209,	"Rečica ob Savinji");
        Add(201,	"Renče - Vogrsko");
        Add(104,	"Ribnica");
        Add(177,	"Ribnica na Pohorju");
        Add(106,	"Rogaška Slatina");
        Add(105,	"Rogašovci");
        Add(107,	"Rogatec");
        Add(108,	"Ruše");
        Add(178,	"Selnica ob Dravi");
        Add(109,	"Semič");
        Add(110,	"Sevnica");
        Add(111,	"Sežana");
        Add(112,	"Slovenj Gradec");
        Add(113,	"Slovenska Bistrica");
        Add(114,	"Slovenske Konjice");
        Add(179,	"Sodražica");
        Add(180,	"Solčava");
        Add(202,	"Središče ob Dravi");
        Add(115,	"Starše");
        Add(203,	"Straža");
        Add(204,	"Sv. Trojica v Slov. Goricah");
        Add(181,	"Sveta Ana");
        Add(182,	"Sveti Andraž v Slov. Goricah");
        Add(116,	"Sveti Jurij ob Ščavnici");
        Add(210,	"Sveti Jurij v Slov. Goricah");
        Add(205,	"Sveti Tomaž");
        Add(33,	"Šalovci");
        Add(183,	"Šempeter - Vrtojba");
        Add(117,	"Šenčur");
        Add(118,	"Šentilj");
        Add(119,	"Šentjernej");
        Add(120,	"Šentjur pri Celju");
        Add(211,	"Šentrupert");
        Add(121,	"Škocjan");
        Add(122,	"Škofja Loka");
        Add(123,	"Škofljica");
        Add(124,	"Šmarje pri Jelšah");
        Add(206,	"Šmarješke Toplice");
        Add(125,	"Šmartno ob Paki");
        Add(194,	"Šmartno pri Litiji");
        Add(126,	"Šoštanj");
        Add(127,	"Štore");
        Add(184,	"Tabor");
        Add(10,	"Tišina");
        Add(128,	"Tolmin");
        Add(129,	"Trbovlje");
        Add(130,	"Trebnje");
        Add(185,	"Trnovska vas");
        Add(186,	"Trzin");
        Add(131,	"Tržič");
        Add(132,	"Turnišče");
        Add(133,	"Velenje");
        Add(187,	"Velika Polana");
        Add(134,	"Velike Lašče");
        Add(188,	"Veržej");
        Add(135,	"Videm");
        Add(136,	"Vipava");
        Add(137,	"Vitanje");
        Add(138,	"Vodice");
        Add(139,	"Vojnik");
        Add(189,	"Vransko");
        Add(140,	"Vrhnika");
        Add(141,	"Vuzenica");
        Add(142,	"Zagorje ob Savi");
        Add(143,	"Zavrč");
        Add(144,	"Zreče");
        Add(190,	"Žalec");
        Add(146,	"Železniki");
        Add(191,	"Žetale");
        Add(147,	"Žiri");
        Add(192,	"Žirovnica");
        Add(193,	"Žužemberk");
    }
}
