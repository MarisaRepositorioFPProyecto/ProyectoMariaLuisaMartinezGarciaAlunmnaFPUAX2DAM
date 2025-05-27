package com.example.app_diabetes

import com.example.app_diabetes.EditActivity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.ImageButton
import android.graphics.*
import android.graphics.pdf.PdfDocument
import android.os.Environment
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.Toast
import java.io.File
import java.io.FileOutputStream
import com.example.app_diabetes.Frases
class HomeActivity : AppCompatActivity() {
    private lateinit var dbManager: DatabaseManager

    private lateinit var tableLayout: TableLayout
    private lateinit var btnEdit: Button
    override fun onResume() {
        super.onResume()
        cargarRegistros()

        // filtrado
        val editFiltroNivel = findViewById<EditText>(R.id.editFiltroNivel)

        editFiltroNivel.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val filtro = s.toString()
                if (filtro.isEmpty()) {
                    cargarRegistros() // todos los datos
                } else {
                    filtrarRegistros(filtro)
                }
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }
    private fun filtrarRegistros(filtro: String) {
        tableLayout.removeViews(1, tableLayout.childCount - 1)

        val usuarioId = getSharedPreferences("APP_PREFS", MODE_PRIVATE).getInt("usuario_id", -1)
        val registros = dbManager.getRegistrosByUsuarioId(usuarioId)

        val registrosFiltrados = registros.filter {
            it.nivel.toString().startsWith(filtro) || it.fecha.contains(filtro)
        }
        // aqui es como que creo las filas de nuevo de resultado al filtrar por eso los estilos
        for ((index, registro) in registrosFiltrados.withIndex()) {
            val row = TableRow(this)
            val bgColor = if (index % 2 == 0)
                resources.getColor(android.R.color.white, theme)
            else
                resources.getColor(R.color.colorAccent, theme)

            row.setBackgroundColor(bgColor)

            val tvNivel = TextView(this).apply { text = registro.nivel.toString() }
            val tvInsulina = TextView(this).apply { text = registro.insulina.toString() }
            val tvFecha = TextView(this).apply { text = registro.fecha }
            val tvHora = TextView(this).apply { text = registro.hora }

            val btnNota = ImageButton(this).apply {
                setImageResource(R.drawable.ic_nota)
                background = null
                setOnClickListener {
                    val intent = Intent(this@HomeActivity, NotaActivity::class.java)
                    intent.putExtra("nota", registro.notaLibre)
                    startActivity(intent)
                }
            }

            val btnEditar = ImageButton(this).apply {
                setImageResource(R.drawable.ic_editar)
                background = null
                setOnClickListener {
                    val intent = Intent(this@HomeActivity, ModificarDatoActivity::class.java)
                    intent.putExtra("registro_id", registro.id)
                    startActivity(intent)
                }
            }

            row.addView(tvNivel)
            row.addView(tvInsulina)
            row.addView(tvFecha)
            row.addView(tvHora)
            row.addView(btnNota)
            row.addView(btnEditar)

            tableLayout.addView(row)
        }
    }

    private fun exportarDatosAPdf() {
        val registros = dbManager.getRegistrosByUsuarioId(
            getSharedPreferences("APP_PREFS", MODE_PRIVATE).getInt("usuario_id", -1)
        )

        val pdfDocument = PdfDocument()
        val paint = Paint()
        val pageInfo = PdfDocument.PageInfo.Builder(595, 842, 1).create()
        val page = pdfDocument.startPage(pageInfo)
        val canvas = page.canvas

        var y = 50
        paint.textSize = 14f
        paint.isFakeBoldText = true
        canvas.drawText("Nivel   Insulina   Fecha   Hora  Nota", 40f, y.toFloat(), paint)
        paint.isFakeBoldText = false
        y += 30

        registros.forEach {
            val nota = it.notaLibre?.replace(Regex("Insulina:.*", RegexOption.IGNORE_CASE), "")
                ?.trim()
            val linea = "${it.nivel}   ${it.insulina}  ${it.fecha}   ${it.hora}  $nota"
            canvas.drawText(linea, 40f, y.toFloat(), paint)
            y += 25
        }

        pdfDocument.finishPage(page)

        val path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        val file = File(path, "registros_diabetes.pdf")
        val outputStream = FileOutputStream(file)
        pdfDocument.writeTo(outputStream)
        pdfDocument.close()
        Toast.makeText(this, "PDF guardado en: ${file.absolutePath}", Toast.LENGTH_LONG).show()
    }
    private fun cargarRegistros() {

        tableLayout.removeViews(1, tableLayout.childCount - 1)


        val usuarioId = getSharedPreferences("APP_PREFS", MODE_PRIVATE).getInt("usuario_id", -1)
        val registros = dbManager.getRegistrosByUsuarioId(usuarioId)

        for ((index, registro) in registros.withIndex()) {
            val row = TableRow(this)
            val bgColor = if (index % 2 == 0)
                resources.getColor(android.R.color.white, theme)
            else
                resources.getColor(R.color.colorAccent, theme) // crea este color en colors.xml

            row.setBackgroundColor(bgColor)

            val tvNivel = TextView(this).apply { text = registro.nivel.toString() }
            val tvInsulina = TextView(this).apply { text = registro.insulina.toString() }
            val tvFecha = TextView(this).apply { text = registro.fecha }
            val tvHora = TextView(this).apply { text = registro.hora }
            val btnNota = ImageButton(this).apply {
                setImageResource(R.drawable.ic_nota)
                background = null
                setOnClickListener {
                    val intent = Intent(this@HomeActivity, NotaActivity::class.java)
                    intent.putExtra("nota", registro.notaLibre)
                    startActivity(intent)
                }
            }

            val btnEditar = ImageButton(this).apply {
                setImageResource(R.drawable.ic_editar)
                background = null
                setOnClickListener {
                    val intent = Intent(this@HomeActivity, ModificarDatoActivity::class.java)
                    intent.putExtra("registro_id", registro.id)
                    startActivity(intent)
                }
            }
            row.addView(tvNivel)
            row.addView(tvInsulina)
            row.addView(tvFecha)
            row.addView(tvHora)
            row.addView(btnNota)
            row.addView(btnEditar)

            tableLayout.addView(row)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        dbManager = DatabaseManager(this)



        super.onCreate(savedInstanceState)
        setContentView(R.layout.home)

        tableLayout = findViewById(R.id.tableLayout)
        btnEdit = findViewById(R.id.btnEdit)




        val fraseAleatoria = Frases.frases.random()
        findViewById<TextView>(R.id.subtitle).text = fraseAleatoria

        val usuarioId = getSharedPreferences("APP_PREFS", MODE_PRIVATE).getInt("usuario_id", -1)
        val registros = dbManager.getRegistrosByUsuarioId(usuarioId)

        for (registro in registros) {
            val row = TableRow(this)

            // Celdas
            val tvNivel = TextView(this).apply { text = registro.nivel.toString() }
            val tvInsulina = TextView(this).apply { text = registro.insulina.toString() }
            val tvFecha = TextView(this).apply { text = registro.fecha }
            val tvHora = TextView(this).apply { text = registro.hora }

            val btnNota = ImageButton(this).apply {
                setImageResource(R.drawable.ic_nota)
                background = null
                setOnClickListener {
                    // abre página para ver la nota
                    val intent = Intent(this@HomeActivity, NotaActivity::class.java)
                    intent.putExtra("nota", registro.notaLibre)
                    startActivity(intent)
                }
            }


            val btnEditar = ImageButton(this).apply {
                setImageResource(R.drawable.ic_editar)
                background = null
                setOnClickListener {
                    val intent = Intent(this@HomeActivity, ModificarDatoActivity::class.java)
                    intent.putExtra("registro_id", registro.id)
                    startActivity(intent)
                }
            }
            row.addView(tvNivel)
            row.addView(tvInsulina)
            row.addView(tvFecha)
            row.addView(tvHora)
            row.addView(btnNota)
            row.addView(btnEditar)

            tableLayout.addView(row)
            val btnExportar = findViewById<Button>(R.id.btnExportar)
            btnExportar.setOnClickListener {
                exportarDatosAPdf()
            }
        }

        // Configurar el botón de editar
        btnEdit.setOnClickListener {
            // NUEVO DATO
            val intent = Intent(this, com.example.app_diabetes.EditActivity::class.java)
            startActivity(intent)

        }




    }
    fun preguntasFrecuentes(view: View) {
        val intent = Intent(this, AyudaActivity::class.java)
        startActivity(intent)
    }

    fun miUsuario(view: View) {
        val intent = Intent(this, MiUsuarioActivity::class.java)
        startActivity(intent)
    }

}