package com.rosetta.app.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "translation_history")
public class TranslationHistory
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "translation_id")
    private Long translationId;

    @ManyToOne(fetch = FetchType.LAZY)//Many records in the current table can refer to one record in the parent table.
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "source_text", columnDefinition = "TEXT", nullable = false)
    private String sourceText;

    @Column(name = "source_language", nullable = false)
    private String sourceLanguage;

    @Column(name = "translated_text", columnDefinition = "TEXT", nullable = false)
    private String translatedText;

    @Column(name = "translated_language", nullable = false)
    private String translatedLanguage;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Long createdAt;

    public TranslationHistory(User user, String sourceText, String sourceLanguage, String translatedText, String translatedLanguage)
    {
        this.user = user;
        this.sourceText = sourceText;
        this.sourceLanguage = sourceLanguage;
        this.translatedText = translatedText;
        this.translatedLanguage = translatedLanguage;
    }

    @PrePersist//Only executed first time before INSERT query.
    protected void onCreate()
    {
        this.createdAt = System.currentTimeMillis();
    }

    public TranslationHistory() {}

    public Long getTranslationId()
    {
        return translationId;
    }

    public void setTranslationId(Long translationId)
    {
        this.translationId = translationId;
    }

    public User getUser()
    {
        return user;
    }

    public void setUser(User user)
    {
        this.user = user;
    }

    public String getSourceText()
    {
        return sourceText;
    }

    public void setSourceText(String sourceText)
    {
        this.sourceText = sourceText;
    }

    public String getSourceLanguage()
    {
        return sourceLanguage;
    }

    public void setSourceLanguage(String sourceLanguage)
    {
        this.sourceLanguage = sourceLanguage;
    }

    public String getTranslatedText()
    {
        return translatedText;
    }

    public void setTranslatedText(String translatedText)
    {
        this.translatedText = translatedText;
    }

    public String getTranslatedLanguage()
    {
        return translatedLanguage;
    }

    public void setTranslatedLanguage(String translatedLanguage)
    {
        this.translatedLanguage = translatedLanguage;
    }

    public Long getCreatedAt()
    {
        return createdAt;
    }
}
