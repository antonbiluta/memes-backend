package ru.biluta.memes.service.persistence.model

import jakarta.persistence.*

@Entity
@Table(name = "users")
class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(name = "user_id", nullable = false, unique = true)
    val userId: Long,

    @Column(name = "username")
    val username: String? = null,

    @Column(name = "first_name")
    val firstName: String? = null,

    @Column(name = "last_name")
    val lastName: String? = null,

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    val memes: List<Memes> = mutableListOf()
)