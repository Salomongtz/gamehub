package com.example.gamehub.services.implement;

import com.example.gamehub.models.Customer;
import com.example.gamehub.models.Games;
import com.example.gamehub.models.Purchase;
import com.example.gamehub.models.Purchase_Game;
import com.example.gamehub.records.PurchaseRecord;
import com.example.gamehub.repositories.CustomerRepository;
import com.example.gamehub.repositories.GamesRepository;
import com.example.gamehub.repositories.PurchaseRepository;
import com.example.gamehub.repositories.Purchase_GameRepository;
import com.example.gamehub.services.PurchaseService;


import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
public class PurchaseServiceImplement implements PurchaseService {
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    GamesRepository gamesRepository;
    @Autowired
    Purchase_GameRepository purchaseGameRepository;
    @Autowired
    PurchaseRepository purchaseRepository;

    private static ResponseEntity<String> checkGame(Games game) {
        if (game == null) {
            return ResponseEntity.badRequest().body("Game not found");
        }
        if (game.getPrice() < 0) {
            return ResponseEntity.badRequest().body(game + "Price cannot be negative");
        }
        if (game.getStock() <= 0) {
            return ResponseEntity.badRequest().body(game + "No stock available");
        }
        return null;
    }

    @Override
    public ResponseEntity<?> purchase(Authentication authentication, List<PurchaseRecord> purchaseRecords) throws IOException {
        Customer customer = customerRepository.findByEmail(authentication.getName());
        List<Purchase_Game> purchaseGames = new ArrayList<>();
        double totalAmount = 0d;

        if (customer == null) {
            return ResponseEntity.badRequest().body("Customer not found");
        }

        for (PurchaseRecord purchaseRecord : purchaseRecords) {
            Games game = gamesRepository.findByTitle(purchaseRecord.title());
            ResponseEntity<String> checkGame = checkGame(game);
            if (checkGame != null) return checkGame;
            totalAmount += (game.getPrice() - (game.getPrice() * game.getDiscount())) * purchaseRecord.amount();
            Purchase_Game purchaseGame = new Purchase_Game(purchaseRecord.amount());
            game.setSales(game.getSales() + purchaseRecord.amount());
            game.setStock(game.getStock() - purchaseRecord.amount());
            game.addPurchaseGame(purchaseGame);
            purchaseGames.add(purchaseGame);
            purchaseGameRepository.save(purchaseGame);
            game.setOwned(game.getOwned() + (long) purchaseRecord.amount());
            customer.addGame(game);
            gamesRepository.save(game);
        }

        Purchase purchase = new Purchase(LocalDate.now(), totalAmount);
        purchaseGames.forEach(purchase::addPurchaseGame);

        customer.addPurchase(purchase);
        purchaseRepository.save(purchase);
        purchaseGameRepository.saveAll(purchaseGames);
        getPDFReceipt(authentication.getName(), purchase.getId());
        return ResponseEntity.ok("Purchase successful");
    }

    @Override
    public ResponseEntity<?> getPDFReceipt(String email, Long purchaseId) throws IOException {
        Customer customer = customerRepository.findByEmail(email);
        Purchase purchase = purchaseRepository.findById(purchaseId).orElse(null);
        if (customer == null) {
            return ResponseEntity.badRequest().body("Customer not found");
        }
        if (purchase == null) {
            return ResponseEntity.badRequest().body("Purchase not found");
        }
        //Get customer and purchase data.
        String fullName = customer.getFirstName() + " " + customer.getLastName();
        LocalDate date = purchase.getPurchaseDate();
        double total = purchase.getTotalAmount();
        //Format currency
        NumberFormat format = NumberFormat.getCurrencyInstance(Locale.US);
        String localeFormattedTotal = format.format(total);
        //Get games
        List<Purchase_Game> games = purchase.getPurchaseGames();
        //Create new PDF document
//        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy MM dd A H m s");
        String formatNow = now.format(formatter);
        FileOutputStream fileOutputStream = new FileOutputStream(formatNow.replaceAll(" ", "_") + ".pdf");
        Document document = new Document();
        PdfWriter.getInstance(document, fileOutputStream);
        //Open document
        document.open();
        //Define Fonts
        Font tableFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);
        Font bold = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14);

        //Create table
        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(100);
        //Create table cells
        PdfPCell imageCell = new PdfPCell(), dateCell = new PdfPCell();
        //Set table cells content (image and date)
        Image logo = Image.getInstance("https://i.imgur.com/NIVgJTD.png");
        logo.scaleToFit(PageSize.A4.getWidth() - 350, PageSize.A4.getHeight() - 300);
        imageCell.addElement(logo);
        imageCell.setBorder(Rectangle.NO_BORDER);
        table.addCell(imageCell);
        dateCell.addElement(new Paragraph("This is a receipt generated on " + date.format(DateTimeFormatter.ofPattern("dd-MMM-yyyy")), tableFont));
        dateCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        dateCell.setBorder(Rectangle.NO_BORDER);
        table.addCell(dateCell);
        //Set cell properties (height)
        imageCell.setFixedHeight(Math.max(imageCell.getHeight(), dateCell.getHeight()));
        dateCell.setFixedHeight(Math.max(imageCell.getHeight(), dateCell.getHeight()));
        //Add table to document
        document.add(table);
        //Add space
        document.add(new Paragraph("\n"));
        //Create paragraph with customer name
        Paragraph paragraph = new Paragraph(fullName, bold);
        paragraph.setAlignment(Element.ALIGN_CENTER);
        document.add(paragraph);
        //Add space
        document.add(new Paragraph("\n"));
        //Add purchase date
        paragraph = new Paragraph("Purchase Date: " + date.toString(), tableFont);
        paragraph.setAlignment(Element.ALIGN_LEFT);
        document.add(paragraph);
        //Add space
        document.add(new Paragraph("\n"));
        //Add total amount
        paragraph = new Paragraph("Total Amount: " + localeFormattedTotal, tableFont);
        paragraph.setAlignment(Element.ALIGN_LEFT);
        document.add(paragraph);
        //Add space
        document.add(new Paragraph("\n"));
        //Create table for games
        PdfPTable gamesTable = new PdfPTable(5);
        gamesTable.setWidthPercentage(100);
        //Create table cells
        PdfPCell titleCellHeader = new PdfPCell(), priceCellHeader = new PdfPCell(), amountCellHeader =
                new PdfPCell(), discountCellHeader =
                new PdfPCell(), totalCellHeader =
                new PdfPCell();
        //Set table column titles
        titleCellHeader.addElement(new Paragraph("Title", bold));
        titleCellHeader.setVerticalAlignment(Element.ALIGN_MIDDLE);
        titleCellHeader.setBorder(Rectangle.NO_BORDER);
        priceCellHeader.addElement(new Paragraph("Price", bold));
        priceCellHeader.setVerticalAlignment(Element.ALIGN_MIDDLE);
        priceCellHeader.setBorder(Rectangle.NO_BORDER);
        amountCellHeader.addElement(new Paragraph("Amount", bold));
        amountCellHeader.setVerticalAlignment(Element.ALIGN_MIDDLE);
        amountCellHeader.setBorder(Rectangle.NO_BORDER);
        discountCellHeader.addElement(new Paragraph("Discount", bold));
        discountCellHeader.setVerticalAlignment(Element.ALIGN_MIDDLE);
        discountCellHeader.setBorder(Rectangle.NO_BORDER);
        totalCellHeader.addElement(new Paragraph("Total", bold));
        totalCellHeader.setVerticalAlignment(Element.ALIGN_MIDDLE);
        totalCellHeader.setBorder(Rectangle.NO_BORDER);
        //Add table cells to table
        gamesTable.addCell(titleCellHeader);
        gamesTable.addCell(priceCellHeader);
        gamesTable.addCell(amountCellHeader);
        gamesTable.addCell(discountCellHeader);
        gamesTable.addCell(totalCellHeader);
        //Add games to table
        for (Purchase_Game purchaseGame : games) {
            //Get game data
            String title = purchaseGame.getGame().getTitle();
            double price = purchaseGame.getGame().getPrice();
            String amount = String.valueOf(purchaseGame.getQuantity());
            String discount = purchaseGame.getGame().getDiscount() * 100 + "%";
            double gameTotal =
                    (purchaseGame.getGame().getPrice() - (purchaseGame.getGame().getPrice() * purchaseGame.getGame().getDiscount())) * purchaseGame.getQuantity();
            //Format currencies
            String localeFormattedPrice = format.format(price);
            String localeFormattedGameTotal = format.format(gameTotal);
            //Add game data to table
            PdfPCell titleCell = new PdfPCell(new Phrase(title, tableFont));
            titleCell.setBorder(Rectangle.BOTTOM);  //Add bottom border
            titleCell.setPaddingBottom(5F);  //Add bottom padding
            gamesTable.addCell(titleCell);  //Add title cell to table

            PdfPCell priceCell = new PdfPCell(new Phrase(localeFormattedPrice, tableFont));
            priceCell.setBorder(Rectangle.BOTTOM);  //Add bottom border
            priceCell.setPaddingBottom(5F);  //Add bottom padding
            gamesTable.addCell(priceCell);  //Add price cell to table

            PdfPCell amountCell = new PdfPCell(new Phrase(amount, tableFont));
            amountCell.setBorder(Rectangle.BOTTOM);  //Add bottom border
            amountCell.setPaddingBottom(5F);  //Add bottom padding
            gamesTable.addCell(amountCell);  //Add amount cell to table

            PdfPCell discountCell = new PdfPCell(new Phrase(discount, tableFont));
            discountCell.setBorder(Rectangle.BOTTOM);  //Add bottom border
            discountCell.setPaddingBottom(5F);  //Add bottom padding
            gamesTable.addCell(discountCell);  //Add discount cell to table

            PdfPCell totalCell = new PdfPCell(new Phrase(localeFormattedGameTotal, tableFont));
            totalCell.setBorder(Rectangle.BOTTOM);  //Add bottom border
            totalCell.setPaddingBottom(5F);  //Add bottom padding
            gamesTable.addCell(totalCell);  //Add total cell to table

        }
        //Add table to document
        document.add(gamesTable);
        //Add total amount paragraph
        paragraph = new Paragraph("Total Amount: " + localeFormattedTotal, bold);
        paragraph.setAlignment(Element.ALIGN_RIGHT);
        document.add(paragraph);
        //Close document
        document.close();

        return new ResponseEntity<>("PDF Receipt Created", HttpStatus.OK);
    }
}
