# JPA & Lombok Cheat Sheet

## 1️⃣ Typical Lombok Annotations

```java
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
@Entity
```

## 2️⃣ Typical JPA Annotations

```plaintext
@Entity
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
@OneToOne(mappedBy = "person", cascade = CascadeType.ALL)
@OneToMany(mappedBy = "person", cascade = CascadeType.ALL)
@ManyToOne
@ManyToMany
@JoinTable(...)  // for Many-to-Many owning side
@Builder.Default   // required for initializing collections when using @Builder
@ToString.Exclude  // to avoid infinite recursion in bidirectional relationships

```

