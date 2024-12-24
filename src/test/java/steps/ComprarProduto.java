package steps;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.Arrays;

import org.openqa.selenium.Point;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.options.BaseOptions;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.E;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;

public class ComprarProduto {

    private AndroidDriver driver;

    private URL getUrl() {
        try {
            return new URL(
                    "https://cardoso:3b2d740d-8cdf-459e-ab8d-6a79aa895b36@ondemand.us-west-1.saucelabs.com:443/wd/hub");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void arrastaParaCima(Integer xInicio, Integer yInicio, Integer xFim, Integer yFim) {
        // Arrastar para cima
        final var finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        var start = new Point(xInicio, yInicio);
        var end = new Point(xFim, yFim);
        var swipe = new Sequence(finger, 1);
        swipe.addAction(finger.createPointerMove(Duration.ofMillis(0),
                PointerInput.Origin.viewport(), start.getX(), start.getY()));
        swipe.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        swipe.addAction(finger.createPointerMove(Duration.ofMillis(1000),
                PointerInput.Origin.viewport(), end.getX(), end.getY()));
        swipe.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
        driver.perform(Arrays.asList(swipe));
    }

    @Before
    public void iniciar() {
        @SuppressWarnings("rawtypes")
        var options = new BaseOptions()
                .amend("platformName", "Android")
                .amend("appium:platformVersion", "9.0")
                .amend("appium:deviceName", "Samsung Galaxy S9 FHD GoogleAPI Emulator")
                .amend("appium:deviceOrientation", "portrait")
                .amend("appium:app", "storage:filename=mda-2.2.0-25.apk")
                .amend("appium:appPackage", "com.saucelabs.mydemoapp.android")
                .amend("appium:appActivity", "com.saucelabs.mydemoapp.android.view.activities.SplashActivity")
                .amend("appium:automationName", "UiAutomator2")
                .amend("browserName", "")
                .amend("appium:ensureWebviewsHavePages", true)
                .amend("appium:nativeWebScreenshot", true)
                .amend("appium:newCommandTimeout", 3600)
                .amend("appium:connectHardwareKeyboard", true);
        // esta linha instancia o Appium e abre o aplicativo
        driver = new AndroidDriver(this.getUrl(), options);
    }

    @After
    public void finalizar() throws InterruptedException {
        driver.quit();
        Thread.sleep(10000);
    }

    @Dado("que acesso o My Demo App")
    public void que_acesso_o_my_demo_app() {
        // o app foi aberto no final do método iniciar (Before)
    }

    @E("verifico o logo e o nome da secao")
    public void verifico_o_logo_e_o_nome_da_secao() {
        var imgLogo = driver.findElement(AppiumBy.id("com.saucelabs.mydemoapp.android:id/mTvTitle"));
        assertEquals(imgLogo.isDisplayed(), true);

        var lblTituloSecao = driver.findElement(AppiumBy.id("com.saucelabs.mydemoapp.android:id/productTV"));
        assertEquals("Products", lblTituloSecao.getText());
    }

    @E("localizo o {string} que na {int} esta na posicao {int} por {string}")
    public void localizo_o_que_esta_por(String produto, Integer rolagem, Integer num_produto, String preco) {
        // Home
        // produto :
        // preco :

        for (int i = 0; i < rolagem; i++) {
            arrastaParaCima(535, 1900, 535, 800);
        }

        assertEquals(produto, driver.findElement(AppiumBy.xpath(
                "//android.widget.TextView[@content-desc=\"Product Title\" and @text=\"" + produto + "\"]")).getText());

        assertEquals(preco,
                driver.findElement(AppiumBy
                        .xpath("(//android.widget.TextView[@content-desc=\"Product Price\"])[" + num_produto + "]"))
                        .getText());

    }

    @Quando("clico na imagem do {int}")
    public void clico_na_imagem_do(Integer num_produto) {
        driver.findElement(
                AppiumBy.xpath("(//android.widget.ImageView[@content-desc=\"Product Image\"])[" + num_produto + "]"))
                .click();
    }

    @Entao("na tela do produto verifico o {string} e o {string}")
    public void na_tela_do_produto_verifico_o_e_o(String produto, String preco) {
        assertEquals(produto,
                driver.findElement(AppiumBy.id("com.saucelabs.mydemoapp.android:id/productTV")).getText());
        assertEquals(preco, driver.findElement(AppiumBy.id("com.saucelabs.mydemoapp.android:id/priceTV")).getText());
    }

    @Quando("arrasto para cima e clico no botao Add Cart")
    public void arrasto_para_cima_e_clico_no_botao_add_cart() {
        // Tela do Produto
        // botao adicionar no carrinho:

        // Arrastar para cima
        arrastaParaCima(525, 1698, 530, 563);

        // Clicar no botão Add to Cart
        driver.findElement(AppiumBy.id("com.saucelabs.mydemoapp.android:id/cartBt")).click();

        // Verificar se o número de produtos no carrinho mudou para 1
        assertEquals("1", driver.findElement(AppiumBy.id("com.saucelabs.mydemoapp.android:id/cartTV")).getText());

        // Clicar no icone do carrinho de compras para ir para a sua tela
        driver.findElement(AppiumBy.id("com.saucelabs.mydemoapp.android:id/cartIV")).click();
    }

    @Entao("na tela do carrinho verifico o {string} {string} e {int}")
    public void na_tela_do_carrinho_verifico_o_e(String produto, String preco, Integer quantidade)
            throws InterruptedException {
        // Carrinho
        // produto :
        // preco :
        // quant :
        Thread.sleep(2000); // pausa forçada de 2 segundos
        // Verificar o titulo da seção
        assertEquals("My Cart",
                driver.findElement(AppiumBy.id("com.saucelabs.mydemoapp.android:id/productTV")).getText());

        // Verificar o titulo do produto
        assertEquals(produto, driver.findElement(AppiumBy.id("com.saucelabs.mydemoapp.android:id/titleTV")).getText());

        // Verificar o preco do produto
        assertEquals(preco, driver.findElement(AppiumBy.id("com.saucelabs.mydemoapp.android:id/priceTV")).getText());

        // Verificar a quantidade
        assertEquals(quantidade + " Items",
                driver.findElement(AppiumBy.id("com.saucelabs.mydemoapp.android:id/itemsTV")).getText());

        // Verificar o preço total
        assertEquals(preco,
                driver.findElement(AppiumBy.id("com.saucelabs.mydemoapp.android:id/totalPriceTV")).getText());

    }
}