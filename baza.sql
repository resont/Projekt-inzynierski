USE [master]
GO
DECLARE @DatabaseName nvarchar(50)
SET @DatabaseName = N'ProjektInzynierski'

DECLARE @SQL varchar(max)

SELECT @SQL = COALESCE(@SQL,'') + 'Kill ' + Convert(varchar, SPId) + ';'
FROM MASTER..SysProcesses
WHERE DBId = DB_ID(@DatabaseName) AND SPId <> @@SPId

--SELECT @SQL
EXEC(@SQL)
DROP DATABASE [ProjektInzynierski]
GO
/****** Object:  Database [ProjektInzynierski]    Script Date: 17.04.2020 23:47:49 ******/
CREATE DATABASE ProjektInzynierski
GO
ALTER DATABASE [ProjektInzynierski] SET COMPATIBILITY_LEVEL = 150
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [ProjektInzynierski].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [ProjektInzynierski] SET ANSI_NULL_DEFAULT OFF
GO
ALTER DATABASE [ProjektInzynierski] SET ANSI_NULLS OFF
GO
ALTER DATABASE [ProjektInzynierski] SET ANSI_PADDING OFF
GO
ALTER DATABASE [ProjektInzynierski] SET ANSI_WARNINGS OFF
GO
ALTER DATABASE [ProjektInzynierski] SET ARITHABORT OFF
GO
ALTER DATABASE [ProjektInzynierski] SET AUTO_CLOSE OFF
GO
ALTER DATABASE [ProjektInzynierski] SET AUTO_SHRINK OFF
GO
ALTER DATABASE [ProjektInzynierski] SET AUTO_UPDATE_STATISTICS ON
GO
ALTER DATABASE [ProjektInzynierski] SET CURSOR_CLOSE_ON_COMMIT OFF
GO
ALTER DATABASE [ProjektInzynierski] SET CURSOR_DEFAULT  GLOBAL
GO
ALTER DATABASE [ProjektInzynierski] SET CONCAT_NULL_YIELDS_NULL OFF
GO
ALTER DATABASE [ProjektInzynierski] SET NUMERIC_ROUNDABORT OFF
GO
ALTER DATABASE [ProjektInzynierski] SET QUOTED_IDENTIFIER OFF
GO
ALTER DATABASE [ProjektInzynierski] SET RECURSIVE_TRIGGERS OFF
GO
ALTER DATABASE [ProjektInzynierski] SET  DISABLE_BROKER
GO
ALTER DATABASE [ProjektInzynierski] SET AUTO_UPDATE_STATISTICS_ASYNC OFF
GO
ALTER DATABASE [ProjektInzynierski] SET DATE_CORRELATION_OPTIMIZATION OFF
GO
ALTER DATABASE [ProjektInzynierski] SET TRUSTWORTHY OFF
GO
ALTER DATABASE [ProjektInzynierski] SET ALLOW_SNAPSHOT_ISOLATION OFF
GO
ALTER DATABASE [ProjektInzynierski] SET PARAMETERIZATION SIMPLE
GO
ALTER DATABASE [ProjektInzynierski] SET READ_COMMITTED_SNAPSHOT OFF
GO
ALTER DATABASE [ProjektInzynierski] SET HONOR_BROKER_PRIORITY OFF
GO
ALTER DATABASE [ProjektInzynierski] SET RECOVERY FULL
GO
ALTER DATABASE [ProjektInzynierski] SET  MULTI_USER
GO
ALTER DATABASE [ProjektInzynierski] SET PAGE_VERIFY CHECKSUM
GO
ALTER DATABASE [ProjektInzynierski] SET DB_CHAINING OFF
GO
ALTER DATABASE [ProjektInzynierski] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF )
GO
ALTER DATABASE [ProjektInzynierski] SET TARGET_RECOVERY_TIME = 60 SECONDS
GO
ALTER DATABASE [ProjektInzynierski] SET DELAYED_DURABILITY = DISABLED
GO
EXEC sys.sp_db_vardecimal_storage_format N'ProjektInzynierski', N'ON'
GO
ALTER DATABASE [ProjektInzynierski] SET QUERY_STORE = OFF
GO
USE [ProjektInzynierski]
GO
/****** Object:  Table [dbo].[Answers]    Script Date: 17.04.2020 23:47:49 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Answers](
	[a_id] [int] IDENTITY(1,1) NOT NULL,
	[a_answer] [varchar](250) NULL,
	[a_count] [int] DEFAULT 0 NULL,
	[a_q_id] [int] NULL,
 CONSTRAINT [PK_Answers] PRIMARY KEY CLUSTERED
(
	[a_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Conn_us_su]    Script Date: 17.04.2020 23:47:49 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Conn_us_su](
	[con_id] [int] IDENTITY(1,1) NOT NULL,
	[con_u_id] [int] NULL,
	[con_s_id] [int] NULL,
	[con_answer] [bit] NULL
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Question_type]    Script Date: 17.04.2020 23:47:49 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Question_type](
	[qt_it] [int] IDENTITY(1,1) NOT NULL,
	[qt_name] [varchar](50) NULL,
 CONSTRAINT [PK_Question_type] PRIMARY KEY CLUSTERED
(
	[qt_it] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Questions]    Script Date: 17.04.2020 23:47:49 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Questions](
	[q_id] [int] IDENTITY(1,1) NOT NULL,
	[q_question] [varchar](100) NULL,
	[q_type] [int] NULL,
	[q_s_id] [int] NULL,
 CONSTRAINT [PK_Questions] PRIMARY KEY CLUSTERED
(
	[q_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Survey]    Script Date: 17.04.2020 23:47:49 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Survey](
	[s_id] [int] IDENTITY(1,1) NOT NULL,
	[s_topic] [varchar](100) NULL,
	[s_description] [varchar](max) NULL,
 CONSTRAINT [PK_Survey] PRIMARY KEY CLUSTERED
(
	[s_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Users]    Script Date: 17.04.2020 23:47:49 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Users](
	[u_id] [int] IDENTITY(1,1) NOT NULL,
	[u_email] [varchar](50) NULL,
	[u_password] [varchar](150) NULL,
	[u_uuid] [varchar](50) NULL,
	[u_validTo] [date] NULL,
	[u_group] [varchar](50) DEFAULT 'user' NULL,
 CONSTRAINT [PK_Users] PRIMARY KEY CLUSTERED
(
	[u_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
SET IDENTITY_INSERT [dbo].[Question_type] ON

INSERT [dbo].[Question_type] ([qt_it], [qt_name]) VALUES (1, N'text')
INSERT [dbo].[Question_type] ([qt_it], [qt_name]) VALUES (2, N'radio')
INSERT [dbo].[Question_type] ([qt_it], [qt_name]) VALUES (3, N'checkbox')
SET IDENTITY_INSERT [dbo].[Question_type] OFF
SET IDENTITY_INSERT [dbo].[Users] ON

INSERT [dbo].[Users] ([u_id], [u_email], [u_password], [u_uuid], [u_validTo]) VALUES (1, N'test', N'9ece086e9bac491fac5c1d1046ca11d737b92a2b2ebd93f005d7b710110c0a678288166e7fbe796883a4f2e9b3ca9f484f521d0ce464345cc1aec96779149c14', NULL, NULL)
SET IDENTITY_INSERT [dbo].[Users] OFF
ALTER TABLE [dbo].[Answers]  WITH CHECK ADD  CONSTRAINT [FK_Answers_Answers] FOREIGN KEY([a_id])
REFERENCES [dbo].[Answers] ([a_id])
GO
ALTER TABLE [dbo].[Answers] CHECK CONSTRAINT [FK_Answers_Answers]
GO
USE [master]
GO
ALTER DATABASE [ProjektInzynierski] SET  READ_WRITE
GO
